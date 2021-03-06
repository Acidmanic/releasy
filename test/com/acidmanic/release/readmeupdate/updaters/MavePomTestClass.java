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
package com.acidmanic.release.readmeupdate.updaters;

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.release.versions.SemanticVersion;
import com.acidmanic.release.versions.Version;
import java.io.File;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class MavePomTestClass {

    protected final String fakePOMContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" + "    <modelVersion>4.0.0</modelVersion>\n" + "    <groupId>com.acidmanic</groupId>\n" + "    <artifactId>fakeArtifactId</artifactId>\n" + "    <version>1.0-SNAPSHOT</version>\n" + "    <packaging>jar</packaging>\n" + "    <properties>\n" + "        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" + "        <maven.compiler.source>1.8</maven.compiler.source>\n" + "        <maven.compiler.target>1.8</maven.compiler.target>\n" + "    </properties>\n" + "    <dependencies>\n" + "    </dependencies>\n" + "</project>";
    protected final Version version = new SemanticVersion(1, 2, 3, "zangemadrese");

    public MavePomTestClass() {
        File pom = new File(".").toPath().resolve("pom.xml").toFile();

        if (pom.exists()) {
            pom.delete();
        }

        new FileIOHelper().tryWriteAll(pom, fakePOMContent);
    }

}
