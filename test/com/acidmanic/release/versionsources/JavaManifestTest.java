/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.versionsources;

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.release.directoryscanning.DirectoryScannerBundle;
import java.io.File;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author diego
 */
public class JavaManifestTest {

    @Test
    public void javaManifest_givenAManifestFile_shouldReadVersion() {
        System.out.println("javaManifest_givenAManifestFile_shouldReadVersion");

        String fileContent = "Implementation-Version: 0.4.4\n"
                + "X-COMMENT: Main-Class will be added automatically by build\n"
                + "Manifest-Version: 1.0";

        String tempFile = "manifest.mf";

        new FileIOHelper().tryWriteAll(tempFile, fileContent);

        JavaManifest sut = new JavaManifest();

        DirectoryScannerBundle bundle = new DirectoryScannerBundle()
                .add(new File("."), (d, v, s) -> s.accept(d));

        sut.setup(bundle);

        List<String> versions = sut.getVersions();

        assertEquals(1, versions.size());

        assertEquals(versions.get(0), "0.4.4");

        try {
            new File(tempFile).delete();
        } catch (Exception e) {
        }
    }

    @Test
    public void javaManifest_givenAManifestFile_shouldDetectPresenceAfterSetup() {
        System.out.println("javaManifest_givenAManifestFile_shouldDetectPresenceAfterSetup");

        String fileContent = "Implementation-Version: 0.4.4\n"
                + "X-COMMENT: Main-Class will be added automatically by build\n"
                + "Manifest-Version: 1.0";

        String tempFile = "manifest.mf";

        new FileIOHelper().tryWriteAll(tempFile, fileContent);

        JavaManifest sut = new JavaManifest();

        DirectoryScannerBundle bundle = new DirectoryScannerBundle()
                .add(new File("."), (d, v, s) -> s.accept(d));

        sut.setup(bundle);

        assertTrue(sut.isPresent());

        try {
            new File(tempFile).delete();
        } catch (Exception e) {
        }
    }

    @Test
    public void javaManifest_givenAManifestFile_shouldNotDetectPresenceBeforeSetup() {
        System.out.println("javaManifest_givenAManifestFile_shouldNotDetectPresenceBeforeSetup");

        String fileContent = "Implementation-Version: 0.4.4\n"
                + "X-COMMENT: Main-Class will be added automatically by build\n"
                + "Manifest-Version: 1.0";

        String tempFile = "manifest.mf";

        new FileIOHelper().tryWriteAll(tempFile, fileContent);

        JavaManifest sut = new JavaManifest();

        assertFalse(sut.isPresent());

        try {
            new File(tempFile).delete();
        } catch (Exception e) {
        }
    }

    @Test
    public void javaManifest_NOTgivenAManifestFile_shouldNotDetectPresenceAfterSetup() {
        System.out.println("javaManifest_NOTgivenAManifestFile_shouldNotDetectPresenceAfterSetup");

        File tempFile = new File("manifest.mf");

        if (tempFile.exists()) {
            tempFile.delete();
        }

        JavaManifest sut = new JavaManifest();

        DirectoryScannerBundle bundle = new DirectoryScannerBundle()
                .add(new File("."), (d, v, s) -> s.accept(d));

        sut.setup(bundle);

        assertFalse(sut.isPresent());
    }

}
