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
package com.acidmanic.release.utilities;

import com.acidmanic.parse.QuotationParser;
import com.acidmanic.utilities.Bash;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class GitStdWrapper {

    private String gitDirectoryExtension = "";

    private final File workspace;

    public GitStdWrapper(File workspace) {
        this.workspace = workspace;
        this.gitDirectoryExtension = "--git-dir "
                + workspace.toPath().resolve(".git").toAbsolutePath().toString();
    }

    public GitStdWrapper() {
        this(new File("."));
    }

    public String command(String command) {
//        command = "git " + this.gitDirectoryExtension + " " + command;
//        return new Bash().syncRun(command);

        return new Bash().syncRun("git " + command, workspace);

    }
    
    
    public boolean isCleanDirectory(){
        String result = command("status").trim().toLowerCase();
        
        boolean ret = result.contains("nothing to commit") &&
                (result.contains("working directory clean") ||
                result.contains("working tree clean"));
        
        return ret;
    }

    public boolean isGitRepository() {
        String result = command("status").trim();
        return result.startsWith("On branch");
    }

    public List<String> getAllRemotes() {
        ArrayList<String> repos = new ArrayList<>();
        String[] lines = command("remote -v").trim().split("\n");
        for (String line : lines) {
            line = line.trim().replaceAll("\\s", " ");
            int nd = line.indexOf(" ");
            if (nd > 0) {
                String remote = line.substring(0, nd);
                if (!repos.contains(remote)) {
                    repos.add(remote);
                }
            }
        }
        return repos;
    }

    public String getBranch() {
        String[] resultLines = command("status").trim().split("\n");
        if (resultLines != null && resultLines.length > 0) {
            return resultLines[0].replace("On branch", "").trim();
        }
        return "<branch>";
    }

    public static boolean isGitAvailable() {
        Bash b = new Bash();
        String command = "git version";
        if (b.commandCanBeRunned(command)) {
            String result = b.syncRun(command);
            if (result != null) {
                result = result.replaceAll("\\d", "");
                result = result.replaceAll("\\.", "");
                result = result.trim();
                return result.startsWith("git version") ;
            }
        }
        return false;
    }

    public void addAll() {
        command("add -A");
    }

    public void commit(String message) {
        command("commit -m " + new QuotationParser()
                .escapeAndQoute(message, '\''));
    }

    public void tag(String version, String message) {
        command("tag " + version + " -m "
                + new QuotationParser()
                .escapeAndQoute(message, '\''));
    }

    public void tag(String version) {
        command("tag " + version);
    }

    public boolean tagExists(String tag) {
        return allTags().contains(tag);
    }

    private String allTags() {
        return command("tag -l");
    }

    public List<String> listTags() {
        String[] tags = allTags().split("\\n");
        ArrayList<String> ret = new ArrayList<>();
        ret.addAll(Arrays.asList(tags));
        return ret;
    }

    public void fetchTags() {
        command("fetch --tags");
    }

    public void init() {
        command("init");
    }

}
