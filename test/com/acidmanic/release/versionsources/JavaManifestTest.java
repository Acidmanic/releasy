/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.versionsources;

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.parse.stringcomparison.StringComparison;
import com.acidmanic.release.directoryscanning.DirectoryScannerBundle;
import com.acidmanic.release.utilities.DirectoryScannerBundleExtensions;
import java.io.File;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author diego
 */
public class JavaManifestTest extends VersionSourceFileTest<JavaManifest> {

    @Override
    protected JavaManifest createInstance() {
        return new JavaManifest();
    }

    @Override
    protected void makePresentInTestEnvironment(String version, String... args) {
        String fileContent = "Implementation-Version: "+version+"\n"
                + "X-COMMENT: Main-Class will be added automatically by build\n"
                + "Manifest-Version: 1.0";

        String tempFile = "manifest.mf";

        new FileIOHelper().tryWriteAll(tempFile, fileContent);
    }

    @Override
    protected void removeFromTestEnvironment() {

        List<File> manifests = new DirectoryScannerBundleExtensions(
                new DirectoryScannerBundle()
                        .addCurrentDirectory(new File("."))
        ).getFilesByName("manifest.mf", StringComparison.COMPARE_CASE_INSENSITIVE);

        manifests.forEach(m -> m.delete());
    }


}
