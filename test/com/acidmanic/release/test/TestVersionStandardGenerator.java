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
package com.acidmanic.release.test;

import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versions.standard.VersionStandardBuilder;

/**
 *
 * @author Acidmanic
 */
public class TestVersionStandardGenerator {
    
    
    public VersionStandard makeTestandard(){
        
        VersionStandardBuilder builder = new VersionStandardBuilder();
        
        builder.standardName("Testandard")
                .sectionName("Major").alwaysVisible().defaultValue(1)
                .dotDelimited().mandatory().tagPrefix("v").weightOrder(3)
                .wountReset();

        builder.nextSection()
                .sectionName("Minor").alwaysVisible().defaultValue(0)
                .dotDelimited().mandatory().weightOrder(2)
                .resetsByPredecessors();
        
        VersionStandard testandard = builder.build();
        
        return testandard;
    }
}
