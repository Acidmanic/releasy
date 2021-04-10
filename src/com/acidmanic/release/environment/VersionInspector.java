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

import com.acidmanic.release.directoryscanning.DirectoryScannerBundle;
import com.acidmanic.release.versionsources.VersionSourceFile;
import com.acidmanic.release.utilities.ClassRegistery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Acidmanic
 */
public class VersionInspector {
    
    
    private final DirectoryScannerBundle workspace;

    public VersionInspector(DirectoryScannerBundle workspace) {
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
    
    public HashMap<VersionSourceFile,List<String>> getAllPresentVersionStringsAndFiles(){
        
        List<VersionSourceFile> allPresent = getPresentVersionSourceFiles();
        
        HashMap<VersionSourceFile,List<String>> ret = new HashMap<>();
        
        allPresent.forEach((source) -> {
            List<String> versions = source.getVersions();
            
            ret.put(source, versions);
        });
        return ret;
    }
}
