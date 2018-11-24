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
package com.acidmanic.release.fileeditors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class SpecFileEditorTest {

    private final static String OLD_VERSION = "1.1.0";
    private final static String NEW_VERSION = "2.2.03";

    private final static String VERSION_LINE = "  s.version          = '" + OLD_VERSION + "'";
    private final static String SPEC_CONTENT = "Pod::Spec.new do |s|\n"
            + "  s.name             = 'NamingConventions'\n"
            + VERSION_LINE + "\n"
            + "  s.summary          = 'This provides a solution to parse and convert name/ids from different conventions.'\n"
            + "  s.swift_version    = '4.0'\n"
            + "\n"
            + "  s.description      = <<-DESC\n"
            + "This project defines a way to determine the naming convention has been used for a given string, and convert name/IDs with a specific naming-convention, to its equivalent name/ID in other naming-convention.\n"
            + "                       DESC\n"
            + "  s.homepage         =  'https://github.com/Acidmanic/SwiftNamingConventions'\n"
            + "  s.license          = { :type => 'MIT', :file => 'LICENSE' }\n"
            + "  s.author           = { 'Acidmanic' => 'acidmanic.moayedi@gmail.com' }\n"
            + "  s.source           = { :git => 'https://github.com/Acidmanic/SwiftNamingConventions.git', :tag => s.version }\n"
            + "  s.social_media_url = 'https://about.me/moayedi'\n"
            + "\n"
            + "  s.ios.deployment_target = '9.3'\n"
            + "  s.osx.deployment_target = '10.12'\n"
            + "  s.watchos.deployment_target = \"3.2\"\n"
            + "  s.tvos.deployment_target = '10.2'\n"
            + "\n"
            + "  s.source_files = 'NamingConventions/Classes/**/*'\n"
            + "\n"
            + "end";

    private File specFile;
    private File versionLineFile;
    private String[] specLines;

    public SpecFileEditorTest() throws IOException {

        specFile = new File("test.podspec");
        if (specFile.exists()) {
            try {
                specFile.delete();
            } catch (Exception e) {
            }
        }
        this.specLines = this.SPEC_CONTENT.split("\n");
        Files.write(specFile.toPath(), this.SPEC_CONTENT.getBytes(),
                StandardOpenOption.CREATE);
        this.versionLineFile = new File("version.line");
        if (this.versionLineFile.exists()) {
            try {
                this.versionLineFile.delete();
            } catch (Exception e) {
            }
        }
        Files.write(this.versionLineFile.toPath(), VERSION_LINE.getBytes(), StandardOpenOption.CREATE);

    }

    @Test
    public void shouldOnlyAndExactlyChangeVersionline() throws IOException {
        System.out.println("--------  shouldOnlyAndExactlyChangeVersionline --------");
        SpecFileEditor instance = new SpecFileEditor(this.specFile);
        instance.setVerion(NEW_VERSION);
        List<String> nLines = Files.readAllLines(this.specFile.toPath());
        assertEquals(this.specLines.length, nLines.size());
        String changedLine = "";
        String newValueForLine = "";
        int changedLines = 0;
        for (int i = 0; i < this.specLines.length; i++) {
            if (this.specLines[i].compareTo(nLines.get(i)) != 0) {
                changedLine = this.specLines[i];
                newValueForLine = nLines.get(i);
                changedLines += 1;
            }
        }
        assertEquals(1, changedLines);
        String lineSig = changedLine.replaceAll("\\s", "").toLowerCase();
        lineSig = lineSig.replaceAll(".+\\.version=('|\")(.+)('|\")", "");
        assertEquals(lineSig, "");
        System.out.println(changedLine + " has been updated to " + newValueForLine);
    }

    @Test
    public void shouldReplaceTheVersionInAVersionLine() throws IOException {
        System.out.println("--------  shouldReplaceTheVersionInAVersionLine --------");
        SpecFileEditor instance = new SpecFileEditor(this.versionLineFile);
        instance.setVerion(NEW_VERSION);
        String expected = VERSION_LINE.replace(OLD_VERSION, NEW_VERSION);
        String actual = new String(Files.readAllBytes(this.versionLineFile.toPath()));
        assertEquals(expected, actual);
    }

}
