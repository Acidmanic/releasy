/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.gitbump;

import com.acidmanic.release.test.TestResource;
import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author diego
 */
public class GitBumpBuilderTest {
    
    public GitBumpBuilderTest() {
    }
    
    
    @Test
    public void testGit(){
        
        
        new TestResource().putOutContent("dotgit.zip", new File(".git"));
        
        boolean actual = new File(".git").exists();
        
        assertTrue(actual);
    }
//    
//  
//    /**
//     * Test of checkOut method, of class GitBumpBuilder.
//     */
//    @Test
//    public void testCheckOut() {
//        System.out.println("checkOut");
//        String branch = "";
//        GitBumpBuilder instance = new GitBumpBuilder();
//        GitBumpBuilder expResult = null;
//        GitBumpBuilder result = instance.checkOut(branch);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of increment method, of class GitBumpBuilder.
//     */
//    @Test
//    public void testIncrement() {
//        System.out.println("increment");
//        String sectionName = "";
//        GitBumpBuilder instance = new GitBumpBuilder();
//        GitBumpBuilder expResult = null;
//        GitBumpBuilder result = instance.increment(sectionName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of merge method, of class GitBumpBuilder.
//     */
//    @Test
//    public void testMerge() {
//        System.out.println("merge");
//        String branch = "";
//        GitBumpBuilder instance = new GitBumpBuilder();
//        GitBumpBuilder expResult = null;
//        GitBumpBuilder result = instance.merge(branch);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of pull method, of class GitBumpBuilder.
//     */
//    @Test
//    public void testPull() {
//        System.out.println("pull");
//        String branch = "";
//        GitBumpBuilder instance = new GitBumpBuilder();
//        GitBumpBuilder expResult = null;
//        GitBumpBuilder result = instance.pull(branch);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of push method, of class GitBumpBuilder.
//     */
//    @Test
//    public void testPush() {
//        System.out.println("push");
//        String branch = "";
//        GitBumpBuilder instance = new GitBumpBuilder();
//        GitBumpBuilder expResult = null;
//        GitBumpBuilder result = instance.push(branch);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of build method, of class GitBumpBuilder.
//     */
//    @Test
//    public void testBuild() {
//        System.out.println("build");
//        GitBumpBuilder instance = new GitBumpBuilder();
//        List<GitBumpStep> expResult = null;
//        List<GitBumpStep> result = instance.build();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
