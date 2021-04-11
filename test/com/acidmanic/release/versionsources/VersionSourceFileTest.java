/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.versionsources;

import com.acidmanic.parse.stringcomparison.StringComparison;
import com.acidmanic.release.directoryscanning.DirectoryScannerBundle;
import com.acidmanic.release.utilities.DirectoryScannerBundleExtensions;
import java.io.File;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author diego
 * @param <T> the type of VersionSourceFile to be put to the test
 */
public abstract class VersionSourceFileTest<T extends VersionSourceFile> {
    
    
    
    
    protected abstract T createInstance();
    
    protected abstract void makePresentInTestEnvironment(String version,String...args);
    
    protected abstract void removeFromTestEnvironment();
    
    protected String versionsForTest(){
        return "1.2.3";
    }
    
    private final String version = versionsForTest();
    
    
    protected void deleteAnyFile(String name,int comparison){
        List<File> files = new DirectoryScannerBundleExtensions(
                new DirectoryScannerBundle()
                        .addCurrentDirectory(new File("."))
        ).getFilesByName(name, comparison);

        files.forEach(m -> m.delete());
    }
    
    protected void deleteAnyFile(String extension){
        List<File> files = new DirectoryScannerBundleExtensions(
                new DirectoryScannerBundle()
                        .addCurrentDirectory(new File("."))
        ).getFilesByExtension(extension);

        files.forEach(m -> m.delete());
    }
    
    @Test
    public void versionSource_givenVersionFileAvailable_shouldReadVersion() {
        System.out.println("versionSource_givenVersionFileAvailable_shouldReadVersion");

        makePresentInTestEnvironment(version);
                
        T sut = createInstance();

        DirectoryScannerBundle bundle = new DirectoryScannerBundle()
                .addCurrentDirectory(new File("."));

        sut.setup(bundle);

        List<String> versions = sut.getVersions();

        removeFromTestEnvironment();
        
        assertEquals(1, versions.size());

        assertEquals(versions.get(0), version);
    }
    
    
    @Test
    public void versionSource_givenASourceFilePresent_shouldDetectPresenceAfterSetup() {
        System.out.println("versionSource_givenASourceFilePresent_shouldDetectPresenceAfterSetup");

        makePresentInTestEnvironment(version);
        
        T sut = createInstance();

        DirectoryScannerBundle bundle = new DirectoryScannerBundle()
                .addCurrentDirectory(new File("."));

        sut.setup(bundle);

        assertTrue(sut.isPresent());

        removeFromTestEnvironment();
    }
    
    
    @Test
    public void versionSource_givenASourceFilePresent_shouldNotDetectPresenceBeforeSetup() {
        System.out.println("versionSource_givenASourceFilePresent_shouldNotDetectPresenceBeforeSetup");

        makePresentInTestEnvironment(version);

        T sut = createInstance();

        assertFalse(sut.isPresent());

        removeFromTestEnvironment();
    }
    
    
    @Test
    public void versionSource_NOTgivenAnySourcesPresent_shouldNotDetectPresenceAfterSetup() {
        System.out.println("versionSource_NOTgivenAnySourcesPresent_shouldNotDetectPresenceAfterSetup");

        try {
            removeFromTestEnvironment();
        } catch (Exception e) {
        }

        T sut = createInstance();

        DirectoryScannerBundle bundle = new DirectoryScannerBundle()
                .addCurrentDirectory(new File("."));

        sut.setup(bundle);

        assertFalse(sut.isPresent());
    }
    
    
}
