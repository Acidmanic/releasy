/*
 * Copyright (C) 2018 Mani Moayedi (acidmanic.moayedi@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.acidmanic.release.sourcecontrols;

import com.acidmanic.release.versioncontrols.VersionControl;
import com.acidmanic.release.utilities.Compare;
import com.acidmanic.release.utilities.Result;
import com.acidmanic.release.utilities.delegates.UnSafeAction1;
import com.acidmanic.release.utilities.trying.Trier;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.MergeResult;
import static org.eclipse.jgit.api.MergeResult.MergeStatus.*;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class JGitFacadeSourceControl implements SourceControlSystem, VersionControl {

    public static final int MERGE_RESULT_SUCCESS = 0;
    public static final int MERGE_RESULT_CONFLICT = 1;
    public static final int MERGE_RESULT_FAILURE = 2;

    private static final Path GIT_BRANCH_BASEPATH = Paths.get("refs").resolve("heads");
    private static final Path GIT_TAG_BASEPATH = Paths.get("refs").resolve("tags");

    @Override
    public void acceptLocalChanges(File directory, String description) {
        Git git = tryGetGit(directory);
        if (git != null) {
            try {
                git.add().addFilepattern(".").call();
                git.commit().setMessage(description).call();
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public boolean isPresent(File directory) {
        Git git = tryGetGit(directory);
        if (git != null) {
            try {
                git.status().call();
                return true;
            } catch (Exception e) {
            }
        }
        return false;
    }

    private Git tryGetGit(File directory) {
        try {
            return Git.open(directory);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void markVersion(File directory, String versionString, String message) {
        Git git = tryGetGit(directory);

        if (git != null) {
            String tag = normalizeForTag(versionString);

            try {

                git.tag().setName(tag).setMessage(message).call();

            } catch (Exception e) {
            }
        }
    }

    private String normalizeForTag(String value) {

        value = value.replaceAll("\\s+", "-");

        return value;
    }

    @Override
    public boolean switchBranch(File directory, String name) {
        boolean result = tryCommand(directory, git -> git.checkout()
                .setName(name)
                .setCreateBranch(false)
                .call());
        return result;
    }

    private boolean tryCommand(File directory, UnSafeAction1<Git> consumer) {
        try {

            Git git = tryGetGit(directory);

            consumer.invoke(git);

            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean fetch(File directory, String remote) {

        boolean result = tryCommand(directory, g -> g.fetch().setRemote(remote));

        return result;
    }

    public boolean fetch(File directory) {
        return fetch(directory, "origin");
    }

    public boolean pull(File directory, String branch) {

        return pull(directory, "origin", branch);
    }

    public boolean pull(File directory, String origin, String branch) {

        Git git = tryGetGit(directory);

        Result<PullResult> result = new Trier()
                .tryFunction(() -> git.pull().setRemoteBranchName(branch)
                .setRemoteBranchName(branch)
                .call());

        if (result.isSuccess()) {

            result.getValue().isSuccessful();
        }
        return true;
    }

    private Ref getRefForName(Git git, String name) {

        try {
            List<Ref> refs = git.branchList().call();

            for (Ref ref : refs) {
                String branchname = getBranchName(ref);

                if (branchname.compareTo(name) == 0) {
                    return ref;
                }
            }

            refs = git.tagList().call();

            for (Ref ref : refs) {
                String tagName = getTagName(ref);

                if (tagName.compareTo(name) == 0) {
                    return ref;
                }
            }

        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Merges a branch or tag into current branch
     *
     * @param directory root for .git
     * @param referenceName the name of the branch or the tag to be merged into
     * current branch
     * @return
     */
    public int merge(File directory, String referenceName) {

        Git git = tryGetGit(directory);

        Ref reference = getRefForName(git, referenceName);

        if (reference != null) {

            Result<MergeResult> result = new Trier().tryFunction(()
                    -> git.merge().include(reference).call());

            if (result.isSuccess()) {

                MergeResult.MergeStatus status = result.getValue().getMergeStatus();

                if (Compare.EqualsOne(status, CONFLICTING, CHECKOUT_CONFLICT)) {

                    return MERGE_RESULT_CONFLICT;
                }
                if (Compare.EqualsOne(status, MERGED, MERGED_SQUASHED, ALREADY_UP_TO_DATE, FAST_FORWARD, FAST_FORWARD_SQUASHED)) {

                    return MERGE_RESULT_SUCCESS;
                }
            }
        }
        return MERGE_RESULT_FAILURE;
    }

    public boolean push(File directory, String branch) {

        return push(directory, "origin", branch);
    }

    public boolean push(File directory, String remote, String branch) {

        Git git = tryGetGit(directory);

        synchronized (git) {

            Result<Iterable<PushResult>> result = new Trier().tryFunction(
                    () -> git.push()
                            .setRemote(remote)
                            .add(branch)
                            .call());

            if (result.isSuccess()) {

                for (PushResult pr : result.getValue()) {

                    RemoteRefUpdate.Status status = getUpdateStatus(pr, branch);

                    if (status != null && Compare.EqualsOne(status, RemoteRefUpdate.Status.OK, RemoteRefUpdate.Status.UP_TO_DATE)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean mergeBranchIntoCurrent(File directory, String branchName) {

        return MERGE_RESULT_SUCCESS == merge(directory, branchName);
    }

    @Override
    public boolean updateRemote(File directory, String branchName) {
        return push(directory, branchName);
    }

    @Override
    public boolean updateLocal(File directory, String branchName) {
        return pull(directory, branchName);
    }

    private String getBranchName(Ref ref) {

        String fullRef = ref.getName();

        return getBranchName(fullRef);
    }

    private String getBranchName(String fullRef) {

        Path refPath = Paths.get(fullRef);

        return GIT_BRANCH_BASEPATH
                .relativize(refPath)
                .toString();
    }

    private String getTagName(Ref ref) {

        String fullRef = ref.getName();

        return getTagName(fullRef);
    }

    private String getTagName(String fullRef) {

        Path refPath = Paths.get(fullRef);

        return GIT_TAG_BASEPATH
                .relativize(refPath)
                .toString();
    }

    private RemoteRefUpdate.Status getUpdateStatus(PushResult pushResult, String branch) {

        Collection<RemoteRefUpdate> updates = pushResult.getRemoteUpdates();

        for (RemoteRefUpdate update : updates) {

            String branchName = getBranchName(update.getRemoteName());

            if (branch.compareTo(branchName) == 0) {
                return update.getStatus();
            }

        }
        return null;
    }

}
