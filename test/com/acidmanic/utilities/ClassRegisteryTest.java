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
package com.acidmanic.utilities;

import com.acidmanic.utilities.models.A;
import com.acidmanic.utilities.models.B;
import com.acidmanic.utilities.models.C;
import com.acidmanic.utilities.models.D;
import com.acidmanic.utilities.models.E;
import com.acidmanic.utilities.models.F;
import com.acidmanic.utilities.models.G;
import com.acidmanic.utilities.models.H;
import com.acidmanic.utilities.models.I;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class ClassRegisteryTest {

  
    
    private ClassRegistery makeInitializedInstance(){
        ClassRegistery registery=null;
        try {
            registery = ClassRegistery.class.newInstance();
            registery.add(A.class);
            registery.add(B.class);
            registery.add(C.class);
            registery.add(D.class); //A
            registery.add(E.class); //A
            registery.add(F.class); //A
            registery.add(G.class); //I
            registery.add(H.class); //I
            return registery;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return registery;
    }
    
    @Test
    public void shouldMakeAll8Classes() {
        System.out.println("---- shouldMakeAll8Classes ----");
        List<Object> result = makeInitializedInstance().all();
        assertEquals(8, result.size());
    }

    @Test
    public void shouldMakeThreeABasedClasses() {
        System.out.println("---- shouldMakeThreeABasedClasses ----");
        
        ClassRegistery registery = makeInitializedInstance();
        
        List<A> result = registery.all(A.class);        
        assertEquals(4, result.size());
        assertEquals("A", result.get(0).getClass().getSimpleName());
        assertEquals("D", result.get(1).getClass().getSimpleName());
        assertEquals("E", result.get(2).getClass().getSimpleName());
        assertEquals("F", result.get(3).getClass().getSimpleName());
    }

    @Test
    public void shouldMakeTwoIImplementations() {
        System.out.println("---- shouldMakeTwoIImplementations ----");
        
        ClassRegistery registery = makeInitializedInstance();
        
        List<I> result = registery.all(I.class);
        assertEquals(2, result.size());
        assertEquals("G", result.get(0).getClass().getSimpleName());
        assertEquals("H", result.get(1).getClass().getSimpleName());
    }

}
