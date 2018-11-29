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
package com.acidmanic.release.versionables;

import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.versions.Version;
import com.acidmanic.release.utilities.GitStdWrapper;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class GitTag implements Versionable {

    private boolean gitAvalable = false;
    private boolean isRepository = false;
    private GitStdWrapper git = null;
    private static final int TOP_TAGS_COUNT = 10;

    @Override
    public void setup(File directory, int releaseType) {
        this.gitAvalable = GitStdWrapper.isGitAvailable();
        this.git = new GitStdWrapper(directory);
        this.isRepository = git.isGitRepository();
    }

    @Override
    public boolean setVersion(Version version) {
        if (gitAvalable && isRepository) {
            if (checkLegalVersion(version)) {
                try {
                    setReleaseVersion(version);
                    return true;
                } catch (Exception e) {
                    Logger.log("Unable to set Version: " + e.getClass().getSimpleName(), this);
                }
            } else {
                Logger.log("ERROR: Did not performed a relelase.", this);
                Logger.log("because given version is not legal.", this);
            }
        }
        return false;
    }

    private void setReleaseVersion(Version version) {
        git.addAll();
        git.commit("Set release versions to: "
                + version.getVersionString());
        git.tag(version.getVersionString(), getReleaseMessage());
        Logger.log("All versions set.", this);
        printGitPushCommands(version.getVersionString());
    }

    private static String getReleaseMessage() {
        return "Released at: " + new Date().toString();
    }

    private boolean checkLegalVersion(Version version) {
        git.fetchTags();
        return !git.tagExists(version.getVersionString());
    }

    private void printGitPushCommands(String versionString) {
        List<String> remotes = git.getAllRemotes();
        if (!remotes.isEmpty()) {
            String branch = git.getBranch();
            Logger.log("");
            Logger.log("You can perform release by running followings:");
            for (String remote : remotes) {
                Logger.log("");
                Logger.log("git push " + remote + " " + branch);
                Logger.log("git push " + remote + " " + versionString);
                Logger.log("");
                Logger.log("Or with Single Command:");
                Logger.log("");
                Logger.log("git push " + remote + " " + branch + " --follow-tags");
            }
            Logger.log("");
        }
    }

    @Override
    public boolean isPresent() {
        return isRepository;
    }

    @Override
    public List<String> getVersions() {
        if (gitAvalable && isRepository) {
            List<String> tags = git.listTags();
            if (tags.size() <= TOP_TAGS_COUNT) {
                return tags;
            } else {
                int count = tags.size();
                return tags.subList(count - TOP_TAGS_COUNT, count);
            }
        }
        return new ArrayList<>();
    }

}
