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
package com.acidmanic.release.fileeditors.xmlinplace;

import com.acidmanic.release.fileeditors.inplaceediting.XmlInPlaceEditor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class XmlInPlaceEditorTest {

    private String xmlToParse = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "  xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n"
            + "  <modelVersion>4.0.0</modelVersion>\n"
            + "  <groupId>com.acidmanic.maven</groupId>\n"
            + "  <artifactId>hello-maven</artifactId>\n"
            + "  <packaging>jar</packaging>\n"
            + "  <version>1.0-SNAPSHOT</version>\n"
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

    private String oldVersion = "1.0-SNAPSHOT";
    private String newVersion = "1.2.3-beta";
    
    private String[] versionAddress = {"project","version"};
    
    public XmlInPlaceEditorTest() {
    }

    @Test
    public void shouldGetVersionCorrectly(){
        System.out.println("---- shouldGetVersionCorrectly ----");
        XmlInPlaceEditor editor = new XmlInPlaceEditor();
        String actual = editor.getTagContent(versionAddress, xmlToParse);
        assertEquals(oldVersion, actual);
    }
    
    
    @Test
    public void shouldSetVersionCorrectly(){
        System.out.println("---- shouldGetVersionCorrectly ----");
        XmlInPlaceEditor editor = new XmlInPlaceEditor();
        String xml = editor.setTagContent(versionAddress, xmlToParse, newVersion);
        String actual = editor.getTagContent(versionAddress, xml);
        assertEquals(newVersion, actual);
    }
}
