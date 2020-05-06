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
package com.acidmanic.release.commands.directoryscanning;

import java.io.File;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Acidmanic
 */
public class DirectoryScannerBundle {

    
    private final HashMap<File,DirectoryScanner> scanners;
    
    public DirectoryScannerBundle(){
        
        scanners = new HashMap<>();
    }
    
    public DirectoryScannerBundle addCurrentDirectory(File directory){
        
        DirectoryScanner scanner = new CurrentDirectoryScanner();
        
        scanners.put(directory, scanner);
        
        return this;
    }
    
    public DirectoryScannerBundle addRadically(File directory){
        
        DirectoryScanner scanner = new RadicallyScanner();
        
        scanners.put(directory, scanner);
        
        return this;
    }
    
    public DirectoryScannerBundle addTree(File directory){
        
        DirectoryScanner scanner = new TreeScanner();
        
        scanners.put(directory, scanner);
        
        return this;
    }
    
    public DirectoryScannerBundle add(File directory,DirectoryScanner scanner){
        
        scanners.put(directory, scanner);
        
        return this;
    }
    
    public void scan(Function<File, Boolean> validator, Consumer<File> scanner) {
        
        for(File dir : this.scanners.keySet()){
            
            DirectoryScanner directoryScanner = this.scanners.get(dir);
            
            directoryScanner.scan(dir, validator, scanner);
        }
    }
    
}
