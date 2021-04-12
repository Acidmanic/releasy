/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.versionsources;

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.parse.stringcomparison.StringComparison;

/**
 *
 * @author diego
 */
public class MavenTests extends VersionSourceFileTest<Maven> {

    @Override
    protected Maven createInstance() {
        return new Maven();
    }

    @Override
    protected void makePresentInTestEnvironment(String version, String... args) {
        String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
                + "<project >\n"
                + "    \n"
                + "    <version>"+version+"</version>\n"
                + "    \n"
                + "</project>";

        String tempFile = "pom.xml";

        new FileIOHelper().tryWriteAll(tempFile, fileContent);
    }

    @Override
    protected void removeFromTestEnvironment() {
        deleteAnyFile("pom.xml", StringComparison.COMPARE_CASE_INSENSITIVE);
    }

}
