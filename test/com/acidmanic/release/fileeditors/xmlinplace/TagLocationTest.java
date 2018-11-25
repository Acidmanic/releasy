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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class TagLocationTest {

    private String contet = "firstsecondthird";

    public TagLocationTest() {
    }

    @Test
    public void shouldExtractSecondCorrectly() {
        SubString first = new SubString(0, 5);
        SubString thrid = new SubString(11, 16);
        SubString expected = new SubString(5, 11);
        TagLocation second = new TagLocation(first, thrid);
        SubString actual = second.getContent();
        
        assertEquals(expected.getBeginIndex(), actual.getBeginIndex());
        assertEquals(expected.getEndIndex(), actual.getEndIndex());
        
        System.out.println(contet.substring(actual.getBeginIndex(), actual.getEndIndex()));
    }

}
