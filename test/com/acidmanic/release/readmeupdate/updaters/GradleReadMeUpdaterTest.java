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
import com.acidmanic.release.versions.Version;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class GradleReadMeUpdaterTest extends MavePomTestClass{
    
    public GradleReadMeUpdaterTest() {
    }

    private final String readmeContent = "Some line of text\n"
            + "compile 'com.acidmanic:fakeArtifactId:1.0.0'\n"
            + "and some texts after that.\n"
            + "maybe another gradle entry:\n"
            + "\tcompile 'com.acidmanic:fakeArtifactId:1.0.0'\n"
            + "and end of descriptions.";
    
    private final String replacedContent = "Some line of text\n"
            + "compile 'com.acidmanic:fakeArtifactId:1.2.3-zangemadrese'\n"
            + "and some texts after that.\n"
            + "maybe another gradle entry:\n"
            + "\tcompile 'com.acidmanic:fakeArtifactId:1.2.3-zangemadrese'\n"
            + "and end of descriptions.";
    
    
    @Test
    public void shouldReplaceGradleVersionWithNewOne() {
        System.out.println("------------ shouldReplaceGradleVersionWithNewOne ------------");
        int releaseType = ReleaseTypes.STABLE;
        GradleReadmeUpdater instance = new GradleReadmeUpdater();
        String result = instance.process(readmeContent, version, releaseType);
        assertEquals(replacedContent, result);
    }
    
}
