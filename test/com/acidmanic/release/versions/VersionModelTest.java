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

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Acidmanic
 */
public class VersionModelTest {



    @Test
    public void testToArray() {
        System.out.println("toArray");
        VersionModel instance = new VersionModel(4);
        instance.setValue(0, 1);
        instance.setValue(1, 0);
        instance.setValue(2, 2);
        instance.setValue(3, 3);
        int[] expResult = {1,0,2,3};
        int[] result = instance.toArray();
        assertArrayEquals(expResult, result);
    }

    
    @Test
    public void testToRawValue() {
        System.out.println("toRawValue");
        VersionModel instance = new VersionModel(3);
        instance.setValue(0, 1);
        instance.setValue(1, 2);
        instance.setValue(2, 3);
        instance.setOrder(0, 3);
        instance.setOrder(1, 2);
        instance.setOrder(2, 0);
        long expResult = 1*3+100*2+100*1000*1;
        long result = instance.toRawValue();
        assertEquals(expResult, result);
    }

   
    

}
