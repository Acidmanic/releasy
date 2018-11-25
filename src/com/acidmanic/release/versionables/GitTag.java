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
import com.acidmanic.release.versioning.Version;
import com.acidmanic.release.versioning.Versionable;
import com.acidmanic.utilities.Bash;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class GitTag implements Versionable {

    private boolean gitAvalable = false;
    private boolean isRepository = false;
    private String gitDirectory = "";

    @Override
    public void setDirectory(File directory) {
        this.gitAvalable = checkForGit();
        this.gitDirectory = "--git-dir "
                + directory.toPath().resolve(".git").toAbsolutePath().toString();
        this.isRepository = checkRepository();
    }

    @Override
    public boolean canSetVersion() {
        return gitAvalable && isRepository;
    }

    @Override
    public void setVersion(Version version) {
        if (checkLegalVersion(version)) {
            gitCommand("add -A");
            gitCommand("commit -m 'Set release versions to: "
                    + version.getVersionString() + "'");
            gitCommand("tag " + version.getVersionString() + " -m '"
                    + "Released at: " + new Date().toString() + "'");
            Logger.log("All versions set.", this);
            printGitPushCommands(version.getVersionString());
        } else {
            Logger.log("ERROR: Did not performed a relelase.", this);
            Logger.log("because given version is not legal.", this);
        }
    }

    private boolean checkForGit() {
        Bash b = new Bash();
        String command = "git version";
        if (b.commandCanBeRunned(command)) {
            String result = b.syncRun(command);
            if (result != null) {
                result = result.replaceAll("\\d", "");
                result = result.replaceAll("\\.", "");
                result = result.trim();
                return result.compareTo("git version") == 0;
            }
        }
        return false;
    }

    private boolean checkRepository() {
        String result = gitCommand("status").trim();
        return result.startsWith("On branch");
    }

    private boolean checkLegalVersion(Version version) {
        String allTags = gitCommand("fetch --tags");
        return !allTags.contains(version.getVersionString());
    }

    private String gitCommand(String command) {
        command = "git " + this.gitDirectory + " " + command;
        return new Bash().syncRun(command);
    }

    private List<String> getAllRemotes() {
        ArrayList<String> repos = new ArrayList<>();
        String[] lines = gitCommand("remote -v").split("\n");
        for (String line : lines) {
            line = line.replaceAll("\\s", " ");
            String remote = line.substring(0, line.indexOf(" "));
            if (!repos.contains(remote)) {
                repos.add(remote);
            }
        }
        return repos;
    }

    private String getBranch() {
        String[] resultLines = gitCommand("status").trim().split("\n");
        if (resultLines != null && resultLines.length > 0) {
            return resultLines[0].replace("On branch", "").trim();
        }
        return "<branch>";
    }

    private void printGitPushCommands(String versionString) {
        List<String> remotes = getAllRemotes();
        String branch = getBranch();
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
