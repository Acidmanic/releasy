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
package com.acidmanic.release.utilities;

import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acidmanic
 */
public class ApplicationInfo {
    
    
    
    public File getExecutableJarFile(){
        try {
            File jar = new File(this.getClass().getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
            
            return jar;
        } catch (URISyntaxException ex) {        }
        
        return new File(".").toPath().toAbsolutePath().normalize().toFile();
    }
    
    
    public File getExecutionDirectory(){
        
        File jar = getExecutableJarFile();
        
        if(jar.isDirectory()){
            return jar;
        }
        if(jar.getParentFile() == null){
            
            return jar;
        }
        return jar.getParentFile();
    }
}
