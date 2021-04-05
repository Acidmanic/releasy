/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.functionaltests;

import com.acidmanic.release.utilities.GitStdWrapper;
import java.io.File;
import java.io.IOException;
import org.junit.Assert;

/**
 * This base class provides functionalities to confirm git related operations
 * has been performed correctly. It Uses Standard IO for communicating with
 * installed git on the host system where tests are being executed on.
 *
 * @author diego
 */
public class GitFunctionalTestBase {

    
    protected void assertCleanDirectory(File directory){
        
        GitStdWrapper git = new GitStdWrapper(directory);
        
        Assert.assertTrue(git.isCleanDirectory());
    }
    
    protected void assertDirtyDirectory(File directory){
        
        GitStdWrapper git = new GitStdWrapper(directory);
        
        Assert.assertFalse(git.isCleanDirectory());
    }
    
    protected void introduceFileToLocalDirectory(File localDirectory,String name) throws IOException {
        
        File readme = localDirectory.toPath().resolve(name).toFile();
        
        if(readme.exists()){
            readme.delete();
        }
        
        readme.createNewFile();
    }
    
    
    protected void assertBranch(File directory, String branch) {
        
        GitStdWrapper git = new GitStdWrapper(directory);
        
        String actual = git.getBranch();
        
        Assert.assertEquals(branch,actual);
    }
    
}
