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

import com.acidmanic.io.file.FileSystemHelper;
import com.acidmanic.release.functionaltests.GitFunctionalTestBase;
import com.acidmanic.release.test.TestResource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class JGitFacadeSourceControlFunctionalTest extends GitFunctionalTestBase {

    private File localDirectory;

    public JGitFacadeSourceControlFunctionalTest() {

    }

    private void clearEnvironment() {
        new FileSystemHelper().clearDirectory(".");
    }

    private void createEnvironment() {

        clearEnvironment();

        new TestResource().putOutContent("gitbump-env.zip", new File("."));

        localDirectory = new File("local");
    }

    @Test
    public void shouldReturnTrueForInitializedGitDirectory() {

        createEnvironment();

        JGitFacadeSourceControl sut = new JGitFacadeSourceControl();

        boolean result = sut.isPresent(localDirectory);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFalseForEmptyDirectory() {

        clearEnvironment();

        File noneGitDirectory = new File("local2");

        noneGitDirectory.mkdirs();

        JGitFacadeSourceControl sut = new JGitFacadeSourceControl();

        boolean result = sut.isPresent(noneGitDirectory);

        assertEquals(false, result);

        noneGitDirectory.delete();
    }

    @Test
    public void shouldHaveDirtyDirectoryBeforeAndCleanAfterAccept() throws IOException {

        createEnvironment();

        String addingFileName = UUID.randomUUID().toString();

        introduceFileToLocalDirectory(localDirectory, addingFileName);

        assertDirtyDirectory(localDirectory);

        JGitFacadeSourceControl sut = new JGitFacadeSourceControl();

        sut.acceptLocalChanges(localDirectory, "Some Descriptions");

        assertCleanDirectory(localDirectory);
    }

    @Test
    public void souldCheckOutToMasterBranch() {
        createEnvironment();
        
        JGitFacadeSourceControl sut = new JGitFacadeSourceControl();
        
        sut.switchBranch(localDirectory, "master");
        
        assertBranch(localDirectory,"master");
    }
    
    @Test
    public void souldCheckOutToDevelopBranch() {
        createEnvironment();
        
        JGitFacadeSourceControl sut = new JGitFacadeSourceControl();
        
        sut.switchBranch(localDirectory, "develop");
        
        assertBranch(localDirectory,"develop");
    }

    

}
