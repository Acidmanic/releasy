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
package com.acidmanic.release.versions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acidmanic
 */
public class VersionModel {

    private final int[] sectionValues;
    private final long[] sectionWeights;
    private final int numberOfSections;
    

    public VersionModel(int numberOfSections) {
        this.sectionValues = new int[numberOfSections];
        this.sectionWeights = new long[numberOfSections];
        this.numberOfSections = numberOfSections;
    }

    
    public int[] toArray(){
        int[] ret = new int[this.numberOfSections];
        
        System.arraycopy(this.sectionValues, 0, ret, 0, numberOfSections);
        
        return ret;
    }
    
    public List<Integer> toList() {
        
        ArrayList<Integer> ret = new ArrayList<>();
        
        for(int i =0;i<this.numberOfSections;i++){
            ret.add(this.sectionValues[i]);
        }
        
        return ret;
    }
    
    public long toRawValue(){
        long ret = 0;
        
        for(int i =0;i<this.numberOfSections;i++){
            long value = (long)sectionValues[i];
            
            value *= sectionWeights[i];
            
            ret += value;
        }
        
        return ret;
    }

    public int compareTo(VersionModel version) {
        return compare(this,version);
    }

    public void setValue(int index , int value){
        this.sectionValues[index]=value;
    }
    
    public void setWeight(int index , long value){
        this.sectionWeights[index]=value;
    }
    
    public static int compare(VersionModel v1, VersionModel v2){
        long res =  v1.toRawValue()-v2.toRawValue();
        
        if(res >0) return 1;
        
        if(res<0) return -1;
        
        return 0;
    }

}
