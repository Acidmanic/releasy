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

import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versions.standard.VersionStandardBuilder;
import static org.junit.Assert.fail;

/**
 *
 * @author Acidmanic
 */
public class VersionTestBase {
    
    protected VersionStandard standard;
    
    
    
    protected VersionStandard createTestandard(){
        VersionStandardBuilder builder = new VersionStandardBuilder();
        
        builder.standardName("Testandard")
                .sectionName("Major").alwaysVisible().defaultValue(1)
                .dotDelimited().mandatory().tagPrefix("v").weightOrder(3)
                .wountReset();

        builder.nextSection()
                .sectionName("Minor").alwaysVisible().defaultValue(0)
                .dotDelimited().mandatory().weightOrder(2)
                .resetsByPredecessors();
        
        builder.nextSection()
                .sectionName("Patch").alwaysVisible().defaultValue(0)
                .dashDelimited().mandatory().weightOrder(0)
                .addNamed("alpha").addNamed("beta").addNamed("rc").addNamed("lts")
                .resetsByPredecessors();
        
        return  builder.build();
    }
    
    protected VersionModel createVersion(int... values) {

        VersionModel version = new VersionModel(standard.getSections().size());
        for (int i = 0; i < values.length; i++) {
            version.setValue(i, values[i]);
        }
        for (int i = values.length; i < standard.getSections().size(); i++) {
            version.setValue(i, 0);
        }
        for (int i = 0; i < standard.getSections().size(); i++) {
            version.setOrder(i, standard.getSections().get(i).getGlobalWeightOrder());
        }
        return version;
    }
    
    protected void assertEqual(String expected,VersionModel version){
        String[] parts = expected.split("\\.");
        
        for(int i=0;i<parts.length;i++){
            int value = Integer.parseInt(parts[i]);
            
            if(value!=version.getValue(i)){
                String sep="";
                String actual = "";
                for(int j=0;j<standard.getSections().size();j++){
                    actual += sep+version.getValue(j);
                    sep=".";
                }
                fail("Expected '" + expected + "' But was: '" + actual+"'");
            }
        }
    }
    
    protected void versionsEqual(VersionModel expected, VersionModel actual) {
        String e = simpleString(expected);
        
        String a = simpleString(actual);
        
        if(e.compareTo(a)!=0){
            fail("Expected "+ e + " but was " + a);
        }
        
    }

    private String simpleString(VersionModel version) {
        
        String ret = "";
        String sep ="";
        
        for(int i=0;i<standard.getSections().size();i++){
            ret += sep + Integer.toString(version.getValue(i));
            sep = ",";
        }
        
        return ret;
    }
}
