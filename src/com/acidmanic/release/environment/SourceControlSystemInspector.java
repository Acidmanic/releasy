/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.environment;

import com.acidmanic.release.sourcecontrols.SourceControlSystem;
import com.acidmanic.release.utilities.ClassRegistery;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class SourceControlSystemInspector {

    private final File rootDirectory;

    public SourceControlSystemInspector(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public List<SourceControlSystem> getPresentSourceControlSystems() {

        List<SourceControlSystem> allPresent = new ArrayList<>();

        List<SourceControlSystem> allAvailable
                = ClassRegistery.makeInstance().all(SourceControlSystem.class);

        for (SourceControlSystem sourceControl : allAvailable) {
            if (sourceControl.isPresent(rootDirectory)) {
                allPresent.add(sourceControl);
            }
        }

        return allPresent;

    }

}
