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
package com.acidmanic.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class GitStdWrapperTest {

    private static final String GIT_TEST_RANDOM_FILE = "GIT_TEST_RANDOM_FILE";

    public GitStdWrapperTest() throws IOException {
        Bash b = new Bash();
        File currentDirectory = new File(".");
        File git = currentDirectory.toPath().resolve(".git").toFile();
        if (!git.exists()) {
            b.syncRun("git init");
            File readme = currentDirectory.toPath().resolve("Readme.md").toFile();
            if (!readme.exists()) {
                readme.createNewFile();
            }
            System.out.println(b.syncRun("git add -A"));
            b.syncRun("git commit -m 'Add ReadMe'");
        }

        boolean anyDeleted = false;
        File[] files = currentDirectory.listFiles();
        for (File file : files) {
            if (file.getName().startsWith(GIT_TEST_RANDOM_FILE)) {
                try {
                    file.delete();
                    anyDeleted = true;
                } catch (Exception e) {
                }
            }
        }
        if (anyDeleted) {
            b.syncRun("git commit -m 'remove Temp files from lastRun'");
        }

    }

    private void createNewRandomFile() {
        String name = GIT_TEST_RANDOM_FILE + UUID.randomUUID().toString();
        File f = new File(name);
        try {
            f.createNewFile();
        } catch (Exception e) {
        }
        gitStatus();
    }

    private void gitAddAll() {
        new Bash().syncRun("git add -A");
    }

    private void gitStatus() {
        System.out.println(new Bash().syncRun("git status"));
    }

    @Test
    public void shouldCommitAddedChanges() {
        System.out.println("---- shouldCommitAddedChanges ----");
        createNewRandomFile();
        gitAddAll();
        String searchTag = UUID.randomUUID().toString();
        String message = "Message " + searchTag;
        GitStdWrapper instance = new GitStdWrapper();
        instance.commit(message);
        String result = new Bash().syncRun("git log --oneline -1");
        System.out.println(result);
        assertNotEquals(-1, result.indexOf(searchTag));
    }

    @Test
    public void shouldAddChangesUsingInstance() {
        System.out.println("---- shouldAddChangesUsingInstance ----");
        createNewRandomFile();

        GitStdWrapper instance = new GitStdWrapper();

        instance.addAll();

        String searchTag = UUID.randomUUID().toString();
        String message = "Message " + searchTag;

        System.out.println(new Bash().syncRun("git commit -m '"
                + message + "'"));

        String result = new Bash().syncRun("git log --oneline -1");
        System.out.println(result);
        assertNotEquals(-1, result.indexOf(searchTag));
    }

}
