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
import com.acidmanic.io.file.FileSystemHelper;
import com.acidmanic.utilities.Bash;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class JGitFacadeSourceControlTest {

    private File directory;

    public JGitFacadeSourceControlTest() {

    }

    private void setupTestGit(boolean init) {
        directory = new File("git-test");
        if (directory.exists()) {
            new FileSystemHelper().deleteDirectory(directory.getAbsolutePath());
        }
        directory.mkdirs();
        if (init) {
            System.out.println(new Bash().syncRun("git -C git-test init"));
        }
    }

    private String gitCommand(String command) {
        Bash b = new Bash();
        return b.syncRun("git -C git-test " + command);
    }

    @Test
    public void shouldReturnTrueForInitializedGitDirectory() {
        setupTestGit(true);

        JGitFacadeSourceControl sut = new JGitFacadeSourceControl();

        boolean result = sut.isPresent(directory);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFalseForEmptyDirectory() {
        setupTestGit(false);

        JGitFacadeSourceControl sut = new JGitFacadeSourceControl();

        boolean result = sut.isPresent(directory);

        assertEquals(false, result);
    }

    
    @Test
    public void shouldHaveDirtyDirectoryBeforeAndCleanAfterAccept() throws IOException {
        
        setupTestGit(true);

        String addingFileName = UUID.randomUUID().toString();
        
        addFile(addingFileName);
        
        JGitFacadeSourceControl sut = new JGitFacadeSourceControl();

        sut.acceptLocalChanges(directory, "Some Descriptions");
        
        String clean = gitCommand("status");

        assertEquals(-1, clean.indexOf(addingFileName));
    }

    private void addFile(String name) throws IOException {
        
        File readme = directory.toPath().resolve(name).toFile();
        
        if(readme.exists()){
            readme.delete();
        }
        
        readme.createNewFile();
    }
    
    
}
