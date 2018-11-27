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

import com.acidmanic.release.versions.Version;
import com.acidmanic.utilities.GitStdWrapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class GitTagTest {

    private void delTree(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delTree(f);
            }
        }
        file.delete();

    }

    private final File wsDir;
    private final File gitDir;
    private final String TAG_TO_SET = "v1.0.0-test";
    private final Version VERSION_TO_SET;

    public GitTagTest() throws IOException {
        this.VERSION_TO_SET = new Version() {
            @Override
            public String getVersionString() {
                return TAG_TO_SET;
            }

            @Override
            public boolean tryParse(String versionString) {
                return false;
            }

            @Override
            public int compare(Version v) {
                return 0;
            }
        };

        File here = new File(".");
        wsDir = here.toPath().resolve("git-test").toFile();
        if (wsDir.exists()) {
            delTree(wsDir);
        }
        wsDir.mkdir();
        gitDir = wsDir.toPath().resolve(".git").toFile();
        GitStdWrapper git = new GitStdWrapper(wsDir);
        git.init();
        File readMe = wsDir.toPath().resolve("ReadMe.md").toFile();
        readMe.createNewFile();
        git.addAll();
        git.commit("Add ReadMeFile");

    }

    @Test
    public void shouldReturnTrueForBeingPresent() {
        System.out.println("---- shouldReturnTrueForBeingPresent ----");
        GitTag instance = new GitTag();
        instance.setDirectory(wsDir);
        boolean expected = true;
        boolean actual = instance.isPresent();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnTrueForAddingATag() {
        System.out.println("---- shouldReturnTrueForAddingATag ----");
        GitTag instance = new GitTag();
        instance.setDirectory(wsDir);
        boolean expResult = true;
        boolean result = instance.setVersion(VERSION_TO_SET);
        assertEquals(expResult, result);
    }

    @Test
    public void shouldReadPreviouselySetVersion() {
        System.out.println("---- shouldReadPreviouselySetVersion ----");
        GitTag instance = new GitTag();
        instance.setDirectory(wsDir);
        String expResult = TAG_TO_SET;
        new GitStdWrapper(gitDir).tag(TAG_TO_SET);
        List<String> allVersions = instance.getVersions();
        assertFalse(allVersions.isEmpty());
        String result = allVersions.get(allVersions.size() - 1);
        assertEquals(expResult, result);
    }

}
