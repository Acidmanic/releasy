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
package com.acidmanic.release.commands;

import com.acidmanic.release.versions.standard.VersionSection;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.utilities.Emojies;
import java.util.ArrayList;

/**
 *
 * @author Acidmanic
 */
public class Auto2 extends ReleaseCommandBase2{
    
    
    
    
    
    
    private ArrayList<String> extractChanges(VersionStandard standard) {
        
        ArrayList<String> changes = new ArrayList<>();
        
        for(String arg: this.args){
            
            if(isVersionSectionName(standard,arg)){
                
                changes.add(arg);
            }
        }
        return changes;
    }

    private boolean isVersionSectionName(VersionStandard standard, String name) {
        
        name = name.toLowerCase();
        
        for(VersionSection section : standard.getSections()){
            
            if(section.getSectionName().toLowerCase().compareTo(name)==0){
                return true;
            }
        }
        return false;
    }
    
    private void logRelease(boolean release) {
        if (release) {
            log(" "+Emojies.Symbols.CHECK_MARK+"    Successfully released.");
        } else {
            warning("     Release has not been completed.");
        }
        log("");
    }
}
