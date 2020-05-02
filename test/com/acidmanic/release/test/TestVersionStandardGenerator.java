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

import com.acidmanic.release.versions.standard.VersionSection;
import com.acidmanic.release.versions.standard.VersionStandard;

/**
 *
 * @author Acidmanic
 */
public class TestVersionStandardGenerator {
    
    
    public VersionStandard makeTestandard(){
        VersionSection major = new VersionSection();
        
        major.setDefaultValue(1);
        
        major.setDefaultValueHidden(false);
        
        major.setMandatory(true);
        
        major.setSectionName("Major");
        
        major.setTagPostfix("");
        
        major.setTagPrefix("v");
        
        major.setSeparator(VersionSection.SECTION_SEPARATOR_DOT);
        
        major.setReseters(VersionSection.RESET_BY_NONE);
        
        major.setReseters(VersionSection.RESET_BY_NONE);
        
        VersionSection minor = new VersionSection();
        
        minor.setDefaultValue(1);
        
        minor.setDefaultValueHidden(false);
        
        minor.setMandatory(true);
        
        minor.setSectionName("Minor");
        
        minor.setTagPostfix("");
        
        minor.setTagPrefix("");
        
        minor.setSeparator(VersionSection.SECTION_SEPARATOR_DOT);
        
        
        minor.setReseters(VersionSection.RESET_BY_PREVIOUS);
        
        VersionStandard testandard = new VersionStandard();
        
        testandard.setName("Testandard");
        
        testandard.getSections().add(major);
        
        testandard.getSections().add(minor);
        
        return testandard;
    }
}
