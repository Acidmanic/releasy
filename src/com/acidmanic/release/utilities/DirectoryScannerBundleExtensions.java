/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.utilities;

import com.acidmanic.release.directoryscanning.DirectoryScannerBundle;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class DirectoryScannerBundleExtensions {
    
    private final DirectoryScannerBundle scanner;

    public DirectoryScannerBundleExtensions(DirectoryScannerBundle scanner) {
        this.scanner = scanner;
    }
    
    public List<File> getDirectories(){
        List<File> allDirectories = new ArrayList();

        scanner.scan(f -> f.isDirectory(), f -> allDirectories.add(f));
        
        return allDirectories;
    }
    
}
