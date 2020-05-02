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
package com.acidmanic.release.versions.standard;

/**
 *
 * @author Acidmanic
 */
public class VersionStandardBuilder {
    
    
    private VersionSection currentSection = new VersionSection();
    private final VersionStandard standard = new VersionStandard();


    public VersionStandardBuilder standardName(String name){
        
        standard.setName(name);
        
        return this;
    }
    
    public VersionStandardBuilder nextSection(){
        
        standard.getSections().add(currentSection);
        
        currentSection = new VersionSection();
        
        return this;
    }
    
    public VersionStandardBuilder mandatory(){
        
        currentSection.setMandatory(true);
        
        return this;
    }
    
    public VersionStandardBuilder optional(){
        
        currentSection.setMandatory(false);
        
        return this;
    }
    
    public VersionStandardBuilder tagPrefix(String prefix){
        
        currentSection.setTagPrefix(prefix);
        
        return this;
    }
    
    public VersionStandardBuilder tagPostfix(String postfix){
        
        currentSection.setTagPrefix(postfix);
        
        return this;
    }
    
    public VersionStandardBuilder addNamed(String name){
        
        int index = currentSection.getNamedValues().size();
        
        currentSection.getNamedValues().put(index, name);
        
        return this;
    }
    
    public VersionStandardBuilder defaultValue(int def){
        
        currentSection.setDefaultValue(def);
        
        return this;
    }
    
    public VersionStandardBuilder canHide(){
        
        currentSection.setDefaultValueHidden(true);
        
        return this;
    }
    
    public VersionStandardBuilder alwaysVisible(){
        
        currentSection.setDefaultValueHidden(false);
        
        return this;
    }
    
    public VersionStandardBuilder sectionName(String name){
                
        currentSection.setSectionName(name);
        
        return this;
    }
    
    public VersionStandardBuilder dotDelimited(){
                
        currentSection.setSeparator(VersionSection.SECTION_SEPARATOR_DOT);
        
        return this;
    }
    
    public VersionStandardBuilder dashDelimited(){
                
        currentSection.setSeparator(VersionSection.SECTION_SEPARATOR_DASH);
        
        return this;
    }
    
    public VersionStandardBuilder resetsByAny(){
                
        currentSection.setReseters(VersionSection.RESET_BY_ANY);
        
        return this;
    }
    
    public VersionStandardBuilder resetsByPrevious(){
                
        currentSection.setReseters(VersionSection.RESET_BY_PREVIOUS);
        
        return this;
    }
    
    public VersionStandardBuilder resetsByPredecessors(){
                
        currentSection.setReseters(VersionSection.RESET_BY_PREDECESSORS);
        
        return this;
    }
    
    public VersionStandardBuilder wountReset(){
                
        currentSection.setReseters(VersionSection.RESET_BY_NONE);
        
        return this;
    }
    
    public VersionStandardBuilder resetsBy(String sectionName){
        
        String resetString = currentSection.getReseters();
        
        if(resetString!=null && resetString.length()>0){
            resetString += ",";
        }
        
        resetString += sectionName;
        
        currentSection.setReseters(resetString);
        
        return this;
    }
    
    public VersionStandardBuilder weightOrder(long order){
        
        currentSection.setGlobalWeightOrder(order);
        
        return this;
    }
    
    public VersionStandard build(){
        
        return standard;
    }
}
