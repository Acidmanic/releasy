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

import com.acidmanic.release.fileeditors.inplaceediting.XmlTagFinder;
import com.acidmanic.release.fileeditors.inplaceediting.TagLocation;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class XmlTagFinderTest {

    private String groupIdContent = "junit";

    private String dependencyContent = "\n      <groupId>" + groupIdContent + "</groupId>\n"
            + "      <artifactId>junit</artifactId>\n"
            + "      <version>3.8.1</version>\n"
            + "      <scope>test</scope>\n    ";

    private String dependenciesContent = "\n    <dependency>"
            + dependencyContent
            + "</dependency>\n  ";

    private String content = "<dependencies>"
            + dependenciesContent
            + "</dependencies>";

    public XmlTagFinderTest() {
    }

    @Test
    public void shoulGetDependenciesContentOutOfContent() {
        System.out.println("---- shoulGetDependenciesContentOutOfContent ----");
        XmlTagFinder finder = new XmlTagFinder();
        String expected = dependenciesContent;
        TagLocation location = finder.findOutmost("dependencies", content);
        String actual = content.substring(location.getContent().getBeginIndex(),
                location.getContent().getEndIndex());
        System.out.println("-------------------");
        System.out.println(expected);
        System.out.println("-------------------");
        System.out.println(actual);
        System.out.println("-------------------");
        assertEquals(expected, actual);
    }

    @Test
    public void shoulGetDependencyContentOutOfDependencies() {
        System.out.println("---- shoulGetDependenciesContentOutOfContent ----");
        XmlTagFinder finder = new XmlTagFinder();
        String expected = dependencyContent;
        TagLocation dependencies = finder.findOutmost("dependencies", content);

        TagLocation dependency = finder.find("dependency", content, dependencies.getContent());

        String actual = content.substring(dependency.getContent().getBeginIndex(),
                dependency.getContent().getEndIndex());
        System.out.println("-------------------");
        System.out.println(expected);
        System.out.println("-------------------");
        System.out.println(actual);
        System.out.println("-------------------");
        assertEquals(expected, actual);
    }

}
