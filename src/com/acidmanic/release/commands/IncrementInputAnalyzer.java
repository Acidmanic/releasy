/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.commands;

import com.acidmanic.release.versions.standard.VersionSection;
import com.acidmanic.release.versions.standard.VersionStandard;
import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class IncrementInputAnalyzer {
    
    
    public ArrayList<String> extractChanges(VersionStandard standard, String[] increments) {

        ArrayList<String> changes = new ArrayList<>();

        for (String arg : increments) {

            if (isVersionSectionName(standard, arg)) {

                changes.add(arg);
            }
        }
        return changes;
    }
    
    private boolean isVersionSectionName(VersionStandard standard, String name) {

        name = name.toLowerCase();

        for (VersionSection section : standard.getSections()) {

            if (section.getSectionName().toLowerCase().compareTo(name) == 0) {
                return true;
            }
        }
        return false;
    }
}
