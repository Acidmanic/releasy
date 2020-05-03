/*
 * Copyright (C) 2020 Acidmanic
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
package com.acidmanic.release.versions;

import com.acidmanic.release.versions.tools.VersionParser;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.test.TestVersionStandardGenerator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Acidmanic
 */
public class VersionParserTest extends VersionTestBase{
    
    
    
    public VersionParserTest() {
        
        this.standard = createTestandard();
        
    }
    
    

    @Test
    public void shouldRepresentVersionWithAValidVersionString() {
        System.out.println("shouldRepresentVersionWithAValidVersionString");
        VersionModel version = createVersion(1,2,2);
        VersionParser instance = new VersionParser(standard);
        String expResult = "1.2-rc";
        String result = instance.getVersionString(version);
        assertEquals(expResult, result);
    }

    @Test
    public void shouldRepresentVersionWithAValidTagString() {
        System.out.println("shouldRepresentVersionWithAValidTagString");
        VersionModel version = createVersion(1,2,3);
        VersionParser instance = new VersionParser(standard);
        String expResult = "v1.2-lts";
        String result = instance.getTagString(version);
        assertEquals(expResult, result);
    }

    @Test
    public void shouldParseTheStringToCorrectVersion() throws Exception {
        System.out.println("shouldParseTheStringToCorrectVersion");
        String versionString = "3.0-alpha";
        VersionParser instance = new VersionParser(standard);
        VersionModel expResult = createVersion(3,0,0);
        VersionModel result = instance.parse(versionString);
        versionsEqual(expResult,result);
    }

    @Test
    public void shouldParseTheTagToCorrectVersion() throws Exception {
        System.out.println("shouldParseTheTagToCorrectVersion");
        String versionString = "v1.1-beta";
        VersionParser instance = new VersionParser(standard);
        VersionModel expResult = createVersion(1,1,1);
        VersionModel result = instance.parseTag(versionString);
        versionsEqual(expResult, result);
    }

    @Test
    public void shouldGetCorrectTemplateForVersion() {
        System.out.println("shouldGetCorrectTemplateForVersion");
        VersionParser instance = new VersionParser(standard);
        String expResult = "v<Major>.<Minor>-<Patch>";
        String result = instance.getTemplate(true);
        assertEquals(expResult, result);
    }
    
    @Test
    public void shouldGetCorrectTemplateForTag() {
        System.out.println("shouldGetCorrectTemplateForTag");
        VersionParser instance = new VersionParser(standard);
        String expResult = "<Major>.<Minor>-<Patch>";
        String result = instance.getTemplate(false);
        assertEquals(expResult, result);
    }

    
    
}
