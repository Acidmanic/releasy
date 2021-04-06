/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.gitbump;

import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.io.file.FileSystemHelper;
import com.acidmanic.release.commands.directoryscanning.DirectoryScannerBundle;
import com.acidmanic.release.commands.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.test.TestResource;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versions.Version;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versions.standard.VersionStandards;
import com.acidmanic.release.versionsources.VersionSourceFile;
import com.acidmanic.release.utilities.ClassRegistery;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author diego
 */
public class GitBumpBuilderTest {

    public GitBumpBuilderTest() {
    }
    
    public static class TestVersionSourceFile implements VersionSourceFile {

        private static final ArrayList<String> versions = new ArrayList<>();

        public TestVersionSourceFile() {

        }

        @Override
        public void setup(DirectoryScannerBundle scanners) {
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public boolean setVersion(String versionString) {
            int count = versions.size();
            
            versions.clear();
            
            for(int i =90 ;i<count;i++){
                versions.add(versionString);
            }
            
            return true;
        }

        @Override
        public List<String> getVersions() {
            ArrayList<String> ret = new ArrayList<>();

            ret.addAll(versions);
            
            return ret;
        }

        public static void reset() {

            versions.clear();

            versions.add("1.0.0");

            versions.add("1.0.1");
        }
        
        
        private static boolean isRegistered  = false;
        public static void checkRegistery(){
            if(!isRegistered){
                isRegistered = true;
                ClassRegistery.makeInstance().add(TestVersionSourceFile.class);
            }
        }
    }

    @Test
    public void shouldCheckoutToDevelopAndMaster() {

        Environment env = createEnvironment();

        List<GitBumpStep> gitbump = new GitBumpBuilder()
                .checkOut("master")
                .checkOut("develop")
                .build();
        // first one would be initialize
        Assert.assertEquals(3, gitbump.size());

        Context context = createTestContext(env);

        gitbump.get(0).execute(context);

        gitbump.get(1).execute(context);

        assertFileExistsInLocalRepo(env, "master");

        gitbump.get(2).execute(context);

        assertFileExistsInLocalRepo(env, "develop");

    }

    private void assertFileExistsInLocalRepo(Environment env,
            String branch) {

        boolean result;

        result = env.localRepoDir.toPath()
                .resolve(branch).toFile()
                .exists();

        Assert.assertTrue(result);
    }

    @Test
    public void testIncrement() {
        System.out.println("increment");
        String sectionName = "Minor";
        List<GitBumpStep> gitbump = new GitBumpBuilder()
                .increment(sectionName).build();

        // first one would be initialize
        Assert.assertEquals(2, gitbump.size());

        Environment env = createEnvironment();

        Context context = createTestContext(env);

        gitbump.get(0).execute(context);

        gitbump.get(1).execute(context);
        
        List<String> versions = new TestVersionSourceFile().getVersions();
        
        for(String version : versions){
            Assert.assertEquals("1.1.0",version);
        }

    }

//    
//  
//
//    /**
//     * Test of increment method, of class GitBumpBuilder.
//     */
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
    private Context createTestContext(Environment env) {

        DirectoryScannerBundle scanners = new DirectoryScannerBundle();

        scanners.addCurrentDirectory(env.localRepoDir);

        ReleaseWorkspace workspace = new ReleaseWorkspace(
                scanners,
                env.localRepoDir);

        VersionStandard standard = VersionStandards.SIMPLE_SEMANTIC;

        Context context = new Context(
                env.localRepoDir,
                env.remoteNameInLocal,
                workspace,
                standard);
        
        TestVersionSourceFile.reset();

        return context;
    }

    private class Environment {

        public File localRepoDir;
        public File remoteRepoDir;
        public String remoteNameInLocal;
        public File emptyRepository;

    }

    private Environment createEnvironment() {

        Environment environment = new Environment();

        new FileSystemHelper().clearDirectory(".");

        new TestResource().putOutContent("git-test.env.zip", new File("."));

        environment.emptyRepository = new File("empty");
        environment.localRepoDir = new File("local");
        environment.remoteNameInLocal = "origin";
        environment.remoteRepoDir = new File("remote");
        
        TestVersionSourceFile.checkRegistery();
        
        

        return environment;

    }

}
