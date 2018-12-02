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

import com.acidmanic.release.versions.SemanticVersion;
import com.acidmanic.release.versions.Version;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class CarthageReadmeUpdaterTest {

    private Version version = new SemanticVersion(1, 2, 3, "zangemadrese");

    private String content = "Some Rubish Jibberish Words \n"
            + "```\n"
            + "git \"git@github.com:/Acidmanic/releasy.git\" ~= 0.0.0  \n"
            + "Some Rubish Jibberish Words\n"
            + "Some Rubish Jibberish Words Again\n"
            + "github some text is here\n"
            + "git clone git@github.com:/Acidmanic/releasy.git --submodules\n"
            + "Some Rubish Jibberish Words";

    private String replaced = "Some Rubish Jibberish Words \n"
            + "```\n"
            + "git \"git@github.com:/Acidmanic/releasy.git\" ~= 1.2.3-zangemadrese  \n"
            + "Some Rubish Jibberish Words\n"
            + "Some Rubish Jibberish Words Again\n"
            + "github some text is here\n"
            + "git clone git@github.com:/Acidmanic/releasy.git --submodules\n"
            + "Some Rubish Jibberish Words";

    public CarthageReadmeUpdaterTest() {
    }

    @Test
    public void testProcess() {
        System.out.println("process");
        int releaseType = 0;
        CarthageReadmeUpdater instance = new CarthageReadmeUpdater();
        String result = instance.process(content, version, releaseType);
        assertEquals(replaced, result);
    }

}
