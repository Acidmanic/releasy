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
package com.acidmanic.release.utilities;

import com.acidmanic.release.utilities.AgvtoolStdWrapper;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class AgvtoolStdWrapperTest {

    public AgvtoolStdWrapperTest() {
    }

    private AgvtoolStdWrapper makeInstance() {
        return new AgvtoolStdWrapper(new AgvTestBashFactory());
    }

    @Test
    public void shouldReturnTrueForFakeAgvtoolBash() {
        System.out.println("----  shouldReturnTrueForFakeAgvtoolBash  ----");
        AgvtoolStdWrapper instance = makeInstance();
        boolean expResult = true;
        boolean result = instance.checkAGV();
        assertEquals(expResult, result);
    }

    @Test
    public void shouldReturnFullVersionsAccordingTheFactory() {
        System.out.println("----   shouldReturnFullVersionsAccordingTheFactory   ----");
        AgvtoolStdWrapper instance = makeInstance();
        List<String> result = instance.getFullVersions();
        assertEquals(AgvTestBashFactory.FULL_VERSIONS_COUNT, result.size());
        for (String version : result) {
            assertEquals(AgvTestBashFactory.FULL_VERSIONS, version);
        }
    }

}
