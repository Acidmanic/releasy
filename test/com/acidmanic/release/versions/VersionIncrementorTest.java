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

import com.acidmanic.release.versions.tools.VersionIncrementor;
import org.junit.Test;

/**
 *
 * @author Acidmanic
 */
public class VersionIncrementorTest extends VersionTestBase{

    

    public VersionIncrementorTest() {
        
        this.standard = createTestandard();
    }

    

    @Test
    public void shouldIncrementLastSection() {
        System.out.println("shouldIncrementLastSection");
        VersionModel version = createVersion(1,0,0);
        int index = 2;
        VersionIncrementor instance = new VersionIncrementor(standard);
        VersionModel result = instance.increment(version, index);
        assertEqual("1.0.1",result);
    }

    @Test
    public void shouldIncrementMiddleSectionByName() {
        System.out.println("shouldIncrementMiddleSectionByName");
        VersionModel version =  createVersion(1,0,0);
        VersionIncrementor instance = new VersionIncrementor(standard);
        VersionModel result = instance.increment(version, "minor");
        assertEqual("1.1.0",result);
    }

    @Test
    public void shouldIncrementMasterBySection() {
        System.out.println("shouldIncrementMasterBySection");
        VersionModel version =  createVersion(1,0,0);
        VersionIncrementor instance = new VersionIncrementor(standard);
        VersionModel result = instance.increment(version, standard.getSections().get(0));
        assertEqual("2.0.0",result);
    }
    
    @Test
    public void shouldResetMinorAndPatchByMasterIncrementation() {
        System.out.println("shouldResetMinorAndPatchByMasterIncrementation");
        VersionModel version =  createVersion(1,1,1);
        VersionIncrementor instance = new VersionIncrementor(standard);
        VersionModel result = instance.increment(version, 0);
        assertEqual("2.0.0",result);
    }
    
    @Test
    public void shouldResetPatchByMinorIncrementation() {
        System.out.println("shouldResetPatchByMinorIncrementation");
        VersionModel version =  createVersion(1,1,1);
        VersionIncrementor instance = new VersionIncrementor(standard);
        VersionModel result = instance.increment(version, 1);
        assertEqual("1.2.0",result);
    }
    
    
    @Test
    public void majorIncrementationShouldResetMinorAndPatch() {
        System.out.println("shouldResetPatchByMinorIncrementation");
        VersionModel version =  createVersion(1,1,1);
        VersionIncrementor instance = new VersionIncrementor(standard);
        VersionModel result = instance.increment(version, "major");
        assertEqual("2.0.0",result);
    }
    
    
    @Test
    public void minorIncrementationShouldResetPath() {
        System.out.println("shouldResetPatchByMinorIncrementation");
        VersionModel version =  createVersion(1,1,1);
        VersionIncrementor instance = new VersionIncrementor(standard);
        VersionModel result = instance.increment(version, "minor");
        assertEqual("1.2.0",result);
    }
    
    @Test
    public void patchIncrementationShoulOnlyIncrementPatch() {
        System.out.println("shouldResetPatchByMinorIncrementation");
        VersionModel version =  createVersion(1,1,1);
        VersionIncrementor instance = new VersionIncrementor(standard);
        VersionModel result = instance.increment(version, "patch");
        assertEqual("1.1.2",result);
    }

}
