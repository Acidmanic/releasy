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
package com.acidmanic.utilities;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Acidmanic
 */
public class DirectoryHelper {
    
    
    public static final int SCANE_MODE_CURRENT_DIRECTORY =0;
    public static final int SCANE_MODE_TREE =1;
    public static final int SCANE_MODE_RADICALLY =2;

    public void scan(File directory, Function<File, Boolean> validator, Consumer<File> scanner) {
        
        File[] files = directory.listFiles();

        for (File file : files) {

            if (validator.apply(file)) {

                scanner.accept(file);
            }
        }
    }
    
    public void scanCurrentDirectoryFiles(File directory,Function<File, Boolean> validator, Consumer<File> scanner){
        
        scan(directory, f -> !f.isDirectory() && validator.apply(f),scanner);
    }
    
    public void scanDirectories(File directory,Consumer<File> scanner){
        
        scan(directory, f -> f.isDirectory() ,scanner);
    }
    
    public void scanTreeFiles(File directory, Function<File, Boolean> validator, Consumer<File> scanner){
        
        scanCurrentDirectoryFiles(directory, validator, scanner);
        
        scanDirectories(directory, d -> scanTreeFiles(d, validator, scanner));
    }
    
    
    public void scanRadicallyFiles(File directory, Function<File,Boolean> validator, Consumer<File> scanner){
        File parent = directory;
        
        while(parent != null){
            
            scanCurrentDirectoryFiles(parent, validator, scanner);
            
            parent = parent.getParentFile();
        }
    }
    
    public void scanFiles(File directory, Function<File,Boolean> validator, Consumer<File> scanner,int mode){
        if(mode == SCANE_MODE_CURRENT_DIRECTORY){
            scanCurrentDirectoryFiles(directory, validator, scanner);
        }
        if(mode == SCANE_MODE_TREE){
            scanTreeFiles(directory, validator, scanner);
        }
        if(mode == SCANE_MODE_RADICALLY){
            scanRadicallyFiles(directory, validator, scanner);
        }
    }
}
