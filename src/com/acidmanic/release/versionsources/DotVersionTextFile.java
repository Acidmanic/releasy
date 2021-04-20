/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.versionsources;

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.release.directoryscanning.DirectoryScannerBundle;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class DotVersionTextFile implements VersionSourceFile {

    private final List<File> sources;

    public DotVersionTextFile() {
        this.sources = new ArrayList<>();
    }

    @Override
    public void setup(DirectoryScannerBundle scanners) {

        scanners.scan(file -> file.getName().toLowerCase().equals(".version"),
                file -> sources.add(file));
    }

    @Override
    public boolean isPresent() {
        return !this.sources.isEmpty();
    }

    @Override
    public boolean setVersion(String versionString) {

        boolean result = true;

        for (File file : this.sources) {

            try {

                file.delete();
                Files.write(file.toPath(), versionString.getBytes(), StandardOpenOption.CREATE);

            } catch (Exception e) {

                result = false;
            }
        }
        return result;
    }

    @Override
    public List<String> getVersions() {

        List<String> versions = new ArrayList<>();

        this.sources.forEach(source -> versions.add(
                new FileIOHelper().tryReadAllText(source))
        );
        return versions;
    }

    @Override
    public String getName() {
        return ".version Text File";
    }

}
