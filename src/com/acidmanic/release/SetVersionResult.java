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
package com.acidmanic.release;

import com.acidmanic.release.versionables.VersionSourceFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.lang.model.SourceVersion;

/**
 *
 * @author Acidmanic
 */
public class SetVersionResult {
    
    
    private final HashMap<VersionSourceFile,Boolean> results;

    public SetVersionResult() {
        this.results = new HashMap<>();
    }
    
    public void fail(VersionSourceFile source){
        this.results.put(source, Boolean.FALSE);
    }
    
    public void succeed(VersionSourceFile source){
        this.results.put(source, Boolean.TRUE);
    }
    
    public void add(VersionSourceFile source, boolean success){
        this.results.put(source, success);
    }
    
    public List<VersionSourceFile> getSourceFiles(){
        
        List<VersionSourceFile> ret = new ArrayList<>();
        
        ret.addAll(this.results.keySet());
        
        return ret;
    }
    
    public List<Boolean> getResults(){
        
        List<Boolean> ret = new ArrayList<>();
        
        ret.addAll(this.results.values());
        
        return ret;
    }
}
