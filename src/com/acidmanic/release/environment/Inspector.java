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
package com.acidmanic.release.environment;

import com.acidmanic.release.commands.directoryscanning.Workspace;
import com.acidmanic.release.versionables.VersionSourceFile;
import com.acidmanic.utilities.ClassRegistery;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acidmanic
 */
public class Inspector {
    
    
    private final Workspace workspace;

    public Inspector(Workspace workspace) {
        this.workspace = workspace;
    }
    
    public List<VersionSourceFile> getAvailableVersionSourceFiles(){
        
        List<VersionSourceFile> allAvailable = ClassRegistery.makeInstance()
                .all(VersionSourceFile.class);
        
        return allAvailable;
    }
    
    public List<VersionSourceFile> getPresentVersionSourceFiles(){
        
        List<VersionSourceFile> allAvailable = getAvailableVersionSourceFiles();
        
        List<VersionSourceFile> allPresent = new ArrayList<>();
        
        for(VersionSourceFile src:allAvailable){
            
            src.setup(this.workspace);
            
            if(src.isPresent()){
                
                allPresent.add(src);
            }
        }
        return allPresent;
    }
    
    public List<String> getAllPresentedVersionStrings(){
        
        List<VersionSourceFile> allPresent = getPresentVersionSourceFiles();
        
        List<String> ret = new ArrayList<>();
        
        for(VersionSourceFile source: allPresent){
            
            List<String> versions = source.getVersions();
            
            ret.addAll(versions);
        }
        return ret;
    }
}
