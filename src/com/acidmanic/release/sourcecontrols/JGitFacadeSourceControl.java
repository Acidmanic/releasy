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

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.lightweight.logger.ConsoleLogger;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.lightweight.logger.SilentLogger;
import com.acidmanic.release.versioncontrols.VersionControl;
import com.acidmanic.release.utilities.Compare;
import com.acidmanic.release.utilities.Result;
import com.acidmanic.release.utilities.delegates.UnSafeAction1;
import com.acidmanic.release.utilities.trying.Trier;
import com.acidmanic.release.versioncontrols.MarkVersionResult;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.MergeResult;
import static org.eclipse.jgit.api.MergeResult.MergeStatus.*;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

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

    private static final String DEFAULT_CONFIG = "[filesystem \"Private Build|1.8.0_275|/dev/sda1\"]\n"
            + "	timestampResolution = 1001 microseconds\n"
            + "	minRacyThreshold = 5782 microseconds\n"
            + "[filesystem \"Ubuntu|11.0.10|/dev/sda1\"]\n"
            + "	timestampResolution = 8000 nanoseconds\n"
            + "	minRacyThreshold = 4051 microseconds";

    private final Logger logger = new ConsoleLogger();

    private CredentialsProvider credentialsProvider = CredentialsProvider.getDefault();

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

            File configDir = Paths.get(System.getenv("HOME"))
                    .resolve(".config").resolve("jgit").toFile();

            if (!configDir.exists()) {
                this.logger.log("Creating config directory at: "
                        + configDir.getAbsolutePath());
                configDir.mkdirs();
            }
            File configFile = configDir.toPath().resolve("config").toFile();

            if (!configFile.exists()) {
                this.logger.log("Creating config file at: "
                        + configFile.getAbsolutePath());
                new FileIOHelper().tryWriteAll(configFile, DEFAULT_CONFIG);
            }

            this.logger.log("Config file: " + (configFile.exists() ? "Exists" : "Missing"));

            Git git = Git.open(directory);

            return git;

        } catch (Exception e) {
            this.logger.error("Error accessing local git repository: "
                    + e.getClass().getSimpleName());
        }
        return null;
    }

    private Ref tag(File directory, String tagName, String message) {
        Git git = tryGetGit(directory);

        if (git != null) {

            try {

                return git.tag().setName(tagName).setMessage(message).call();

            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public MarkVersionResult markVersion(File directory, String versionString, String message) {

        String tag = normalizeForTag(versionString);

        deleteTagIfExists(directory, tag);

        Ref tagRef = tag(directory, tag, message);

        if (tagRef != null) {

            MarkVersionResult result = new MarkVersionResult();

            result.setSuccessful(true);

            result.setUpdateSourceControlRemote(() -> push(directory, tag));

            return result;
        }
        return new MarkVersionResult();
    }

    private String normalizeForTag(String value) {

        value = value.replaceAll("\\s+", "-");

        return value;
    }

    @Override
    public boolean switchBranch(File directory, String name) {
        this.logger.log("checking out the branch '" + name
                + "' at directory: " + directory.toPath().toAbsolutePath());
        boolean result = tryCommand(directory, git -> {

            this.logger.log("Fetching...");

            boolean fetchResult = fetch(directory);

            this.logger.log("Fetch " + (fetchResult ? "Succeeded" : "Failed"));

            Ref targetRef = getRefrenceFor(git, name);

            if (targetRef == null) {
                this.logger.error("Could not find any refts for " + name);
            } else {
                this.logger.log("Found ref: " + targetRef.getName() + " for " + name);

                git.checkout()
                        .setName(targetRef.getName())
                        .setCreateBranch(false)
                        .call();
            }
        });
        this.logger.log("checkout " + (result ? "Succeeded" : "Failed"));
        return result;
    }

    private boolean tryCommand(File directory, UnSafeAction1<Git> consumer) {
        try {

            Git git = tryGetGit(directory);

            consumer.invoke(git);

            return true;
        } catch (Exception e) {
            this.logger.error("Error executing command: " + e.getClass().getSimpleName());
            this.logger.error("Details: " + e.getMessage());
        }
        return false;
    }

    public boolean fetch(File directory, String remote) {

        boolean result = false;

        UnSafeAction1<Git> code = (git) -> {
            FetchResult fetchResult = git.fetch()
                    .setCredentialsProvider(credentialsProvider)
                    .setRemote(remote)
                    .call();

            logger.log("Fetch result: ");

            fetchResult.submoduleResults().forEach((s, r) -> {
                logger.log("Submodule: " + s + " -> " + r.getMessages());
            });
        };

        result = tryCommand(directory, code);

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
                .setCredentialsProvider(credentialsProvider)
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

            this.logger.log("merge, referenceName referenced to: " + reference.getName());

            Result<MergeResult> result = new Trier().tryFunction(()
                    -> git.merge().include(reference).call());

            if (result.isSuccess()) {

                this.logger.log("Merge Result succeeded.");

                MergeResult.MergeStatus status = result.getValue().getMergeStatus();

                this.logger.log("merge status: " + status);

                if (Compare.EqualsOne(status, CONFLICTING, CHECKOUT_CONFLICT)) {

                    return MERGE_RESULT_CONFLICT;
                }
                if (Compare.EqualsOne(status, MERGED, MERGED_SQUASHED, ALREADY_UP_TO_DATE, FAST_FORWARD, FAST_FORWARD_SQUASHED)) {

                    return MERGE_RESULT_SUCCESS;
                }
            } else {
                this.logger.log("merge result is not successfull.");
            }
        }
        return MERGE_RESULT_FAILURE;
    }

    public boolean push(File directory, String branch) {

        return push(directory, "origin", branch);
    }

    public boolean push(File directory, String remote, String reference) {

        Git git = tryGetGit(directory);

        synchronized (git) {

            Result<Iterable<PushResult>> result = new Trier().tryFunction(
                    () -> git.push()
                            .setRemote(remote)
                            .add(reference)
                            .setCredentialsProvider(credentialsProvider)
                            .call());

            if (result.isSuccess()) {

                for (PushResult pr : result.getValue()) {

                    RemoteRefUpdate.Status status = getUpdateStatus(pr, reference);

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

    private RemoteRefUpdate.Status getUpdateStatus(PushResult pushResult, String name) {

        Collection<RemoteRefUpdate> updates = pushResult.getRemoteUpdates();

        for (RemoteRefUpdate update : updates) {

            String branchName = getBranchName(update.getRemoteName());

            String tagName = getTagName(update.getRemoteName());

            if (name.compareTo(branchName) == 0 || name.compareTo(tagName) == 0) {
                return update.getStatus();
            }

        }
        return null;
    }

    private Ref getRefrenceFor(Git git, String name) {
        try {

            List<Ref> branches = git.branchList().call();
            List<Ref> tags = git.tagList().call();
            List<Ref> refs = new ArrayList<>();
            refs.addAll(branches);
            refs.addAll(tags);

            this.logger.log(" All available branches: ");
            branches.forEach(ref -> this.logger.log("branch: " + ref.getName()));

            Ref targetRef = null;

            for (Ref ref : refs) {
                String refName = getBranchName(ref);

                if (name.compareTo(refName) == 0) {
                    targetRef = ref;
                    break;
                }
            }
            return targetRef;
        } catch (Exception e) {
            this.logger.error("Error finding reference for " + name
                    + ": " + e.getClass().getSimpleName());
        }
        return null;
    }

    @Override
    public void setCredentials(String username, String password) {

        this.credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
    }

    @Override
    public void resetCredentials() {
        this.credentialsProvider = CredentialsProvider.getDefault();
    }

    private void deleteTagIfExists(File directory, String tagName) {
        Git git = tryGetGit(directory);

        if (git != null) {

            try {

                git.tagDelete()
                        .setTags(tagName)
                        .call();
            } catch (Exception e) {
            }
        }
    }
}
