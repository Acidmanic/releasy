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
package com.acidmanic.release.versionables;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class MavenTest {

    private String pomFileContent = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "  xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n"
            + "  <modelVersion>4.0.0</modelVersion>\n"
            + "  <groupId>com.acidmanic.maven</groupId>\n"
            + "  <artifactId>hello-maven</artifactId>\n"
            + "  <packaging>jar</packaging>\n"
            + "  <version>2.2.2-test-default</version>\n"
            + "  <name>hello-maven</name>\n"
            + "  <url>http://maven.apache.org</url>\n"
            + "  <dependencies>\n"
            + "    <dependency>\n"
            + "      <groupId>junit</groupId>\n"
            + "      <artifactId>junit</artifactId>\n"
            + "      <version>3.8.1</version>\n"
            + "      <scope>test</scope>\n"
            + "    </dependency>\n"
            + "  </dependencies>\n"
            + "</project>";

    private final File wsDir;
    private final String OLD_VERSION = "2.2.2-test-default";

    public MavenTest() {

        wsDir = new File(".");
        File pom = wsDir.toPath().resolve("pom.xml").toFile();

        if (pom.exists()) {
            pom.delete();
        }

        try {
            Files.write(pom.toPath(), pomFileContent.getBytes(), StandardOpenOption.CREATE);
        } catch (Exception e) {
        }

    }

    @Test
    public void shouldReadOldVersionFromPomFile() {
        System.out.println("---- shouldReadOldVersionFromPomFile ----");
        Maven mvn = new Maven();
        mvn.setDirectory(wsDir);
        String expected = OLD_VERSION;
        List<String> versions = mvn.getVersions();
        Assert.assertEquals(1, versions.size());
        String actual = versions.get(0);
        TestCase.assertEquals(expected, actual);
    }

}
