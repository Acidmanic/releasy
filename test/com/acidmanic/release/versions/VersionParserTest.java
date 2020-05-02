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
public class VersionParserTest {
    
    
    private final VersionModel versionModel = new VersionModel(2);
    private final String versionString = "1.0";
    private final String versionTagString = "v1.0";
    private final VersionStandard standard = new TestVersionStandardGenerator().makeTestandard();
    
    public VersionParserTest() {
        versionModel.setValue(0, 1);
        versionModel.setValue(1, 0);
        
        versionModel.setOrder(0, standard.getSections().get(0).getGlobalWeightOrder());
        versionModel.setOrder(1, standard.getSections().get(1).getGlobalWeightOrder());
        
    }
    
    

    @Test
    public void testGetVersionString() {
        System.out.println("getVersionString");
        VersionModel version = versionModel;
        VersionParser instance = new VersionParser(standard);
        String expResult = versionString;
        String result = instance.getVersionString(version);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetTagString() {
        System.out.println("getTagString");
        VersionModel version = versionModel;
        VersionParser instance = new VersionParser(standard);
        String expResult = versionTagString;
        String result = instance.getTagString(version);
        assertEquals(expResult, result);
    }

    @Test
    public void testParse() throws Exception {
        System.out.println("parse");
        String versionString = this.versionString;
        VersionParser instance = new VersionParser(standard);
        VersionModel expResult = versionModel;
        VersionModel result = instance.parse(versionString);
        assertEquals(expResult.toRawValue(), result.toRawValue());
    }

    @Test
    public void testParseTag() throws Exception {
        System.out.println("parseTag");
        String versionString = this.versionTagString;
        VersionParser instance = new VersionParser(standard);
        VersionModel expResult = versionModel;
        VersionModel result = instance.parseTag(versionString);
        assertEquals(expResult.toRawValue(), result.toRawValue());
    }

    @Test
    public void testGetTemplateAsTag() {
        System.out.println("getTemplate");
        VersionParser instance = new VersionParser(standard);
        String expResult = "v<Major>.<Minor>";
        String result = instance.getTemplate(true);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetTemplateNormal() {
        System.out.println("getTemplate");
        VersionParser instance = new VersionParser(standard);
        String expResult = "<Major>.<Minor>";
        String result = instance.getTemplate(false);
        assertEquals(expResult, result);
    }
    
}
