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

import com.acidmanic.release.versions.ReleaseTypes;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class MavenReadmeUpdaterTest extends MavePomTestClass {


    

    private final String readmeContent = "These are some text here"
            + "\nin several lines!"
            + "and a maven dependency xml code in the middle:\n"
            + "```xml\n"
            + "        <dependency>\n"
            + "            <artifactId>fakeArtifactId</artifactId>\n"
            + "            <groupId>com.acidmanic</groupId>\n"
            + "            <version>1.0.0</version>\n"
            + "        </dependency>\n"
            + "```"
            + "just like a readme.md file!\n"
            + "but lets add second one\n"
            + "```xml\n"
            + "        <dependency>\n"
            + "            <artifactId>fakeArtifactId</artifactId>\n"
            + "            <groupId>com.acidmanic</groupId>\n"
            + "            <version>1.0.0</version>\n"
            + "        </dependency>\n"
            + "```\n"
            + "and see if works for two.";

    private final String replacedContent = "These are some text here"
            + "\nin several lines!"
            + "and a maven dependency xml code in the middle:\n"
            + "```xml\n"
            + "        <dependency>\n"
            + "            <artifactId>fakeArtifactId</artifactId>\n"
            + "            <groupId>com.acidmanic</groupId>\n"
            + "            <version>1.2.3-zangemadrese</version>\n"
            + "        </dependency>\n"
            + "```"
            + "just like a readme.md file!\n"
            + "but lets add second one\n"
            + "```xml\n"
            + "        <dependency>\n"
            + "            <artifactId>fakeArtifactId</artifactId>\n"
            + "            <groupId>com.acidmanic</groupId>\n"
            + "            <version>1.2.3-zangemadrese</version>\n"
            + "        </dependency>\n"
            + "```\n"
            + "and see if works for two.";

    public MavenReadmeUpdaterTest() {
        
      
    }

    @Test
    public void testProcess() {
        System.out.println("process");
        int releaseType = ReleaseTypes.STABLE;
        MavenReadmeUpdater instance = new MavenReadmeUpdater();
        String result = instance.process(readmeContent, version, releaseType);
        assertEquals(replacedContent, result);
    }

}
