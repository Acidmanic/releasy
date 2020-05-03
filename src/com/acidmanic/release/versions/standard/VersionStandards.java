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
package com.acidmanic.release.versions.standard;

/**
 *
 * @author Acidmanic
 */
public final class VersionStandards {
    
    
    
    private VersionStandards(){}
    
    public static final VersionStandard SIMPLE_SEMANTIC = new VersionStandardBuilder()
            .standardName("Semantic")
                .sectionName("Major").alwaysVisible().defaultValue(1)
                .dotDelimited().mandatory().tagPrefix("v").weightOrder(3)
                .wountReset()
            .nextSection()
                .sectionName("Minor").alwaysVisible().defaultValue(0)
                .dotDelimited().mandatory().weightOrder(2)
                .resetsByPredecessors()
            .nextSection()
                .sectionName("Fix").alwaysVisible().defaultValue(0)
                .dotDelimited().mandatory().weightOrder(2)
                .resetsByPredecessors()
            .nextSection()
                .sectionName("Semantic").canHide().defaultValue(0)
                .dashDelimited().mandatory().weightOrder(0)
                .addNamed("r").addNamed("alpha").addNamed("beta").addNamed("rc")
                .resetsByPredecessors()
            .build();
            
}
