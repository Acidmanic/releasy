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
package com.acidmanic.release.versions.tools;

import com.acidmanic.release.versions.VersionModel;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versions.standard.VersionSection;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Acidmanic
 */
public class VersionIncrementor {
    
    private final VersionStandard standard;

    public VersionIncrementor(VersionStandard standard) {
        this.standard = standard;
    }
    
    public VersionModel increment(VersionModel version,int index){
        
        VersionSection section = standard.getSections().get(index);
        
        int value = version.getValue(index);
        
        if(section.getNamedValues().isEmpty()){
            
            value += 1;
            
            version.setValue(index, value);
        }else{
            
            Collection<Integer> values = section.getNamedValues().keySet();
            
            int incrementedKey = getFirstLarger(values,value);
            
            version.setValue(index, incrementedKey);
        }
        
        // perform resets
        for(int i=0;i<standard.getSections().size();i++){
            
            if(i!=index){
                VersionSection sec = standard.getSections().get(i);
                
                String resetString = sec.getReseters();
                
                boolean resets = resets(resetString,i,index);
                
                if(resets){
                    if(sec.getNamedValues().isEmpty()){
                        //TODO: get reset value from standard. it can be different thatn default
                        version.setValue(i, 0);
                    }else{
                        int minValue = min(sec.getNamedValues().keySet());
                        
                        version.setValue(i, minValue);
                    }
                }
            }
        }
        return version;
    }
    
    public VersionModel increment(VersionModel version,String sectionName){
        
        int index = indexOf(sectionName);
        //TODO: add not found exception
        return increment(version, index);
    }
    
    public VersionModel increment(VersionModel version,VersionSection section){
        int index = indexOf(section);
        //TODO: add not found exception
        return increment(version, index);
    }
    
    private int indexOf(VersionSection section){
        
        String name = section.getSectionName().toLowerCase();
        
        return indexOf(name);
    }
    
    private int indexOf(String name){
        
        name = name.toLowerCase();
        
        for(int i=0;i<standard.getSections().size();i++){
            
            VersionSection sec = standard.getSections().get(i);
            
            if(sec.getSectionName().toLowerCase().compareTo(name)==0){
                return i;
            }
        }
        return -1;
    }

    private int getFirstLarger(Collection<Integer> values, int value) {
        
        ArrayList<Integer> sortedValues = new ArrayList<>();
        
        sortedValues.addAll(values);
        
        sortedValues.sort((v1,v2)-> v1-v2);
        
        for(int i=0;i<sortedValues.size();i++){
            
            int currentValue = sortedValues.get(i);
            
            if(currentValue>value){
                return currentValue;
            }
        }
        return sortedValues.get(sortedValues.size()-1);
    }

    private boolean resets(String resetString, int checkingIndex, int incrementedIndex) {
        
        String[] resets = resetString.split(",");
        String incrementingName = standard.getSections().get(incrementedIndex).getSectionName().toLowerCase();
        
        if(contains(resets,VersionSection.RESET_BY_ANY)){
            return true;
        }
        if(contains(resets, VersionSection.RESET_BY_PREDECESSORS)){
            if(incrementedIndex<checkingIndex){
                return true;
            }
        }
        if(contains(resets, VersionSection.RESET_BY_NONE)){
            return false;
        }
        if(contains(resets, VersionSection.RESET_BY_PREVIOUS)){
            if(checkingIndex>0){
                int prevIndex =0;
                
                String prevName = standard.getSections().get(prevIndex).getSectionName().toLowerCase();
                if(prevName.compareTo(incrementingName)==0){
                    return true;
                }
            }
        }
        if(contains(resets, incrementingName)){
            return true;
        }
        return false;
    }

    private int min(Collection<Integer> keySet) {
        
        int min = Integer.MAX_VALUE;
        
        for(Integer key : keySet){
            if(key<min){
                min = key;
            }
        }
        return min;
    }

    private boolean contains(String[] array, String search) {
        for(String item : array){
            if(item.compareTo(search)==0){
                return true;
            }
        }
        return false;
    }
    
}
