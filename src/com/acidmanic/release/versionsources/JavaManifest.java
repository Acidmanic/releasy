/*
 * Copyright (C) 2018 Mani Moayedi (acidmanic.moayedi@gmail.com)
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
package com.acidmanic.release.versionsources;

import com.acidmanic.parse.stringcomparison.StringComparison;
import com.acidmanic.release.directoryscanning.DirectoryScannerBundle;
import com.acidmanic.release.fileeditors.ManifestEditor;
import com.acidmanic.release.utilities.DirectoryScannerBundleExtensions;
import com.acidmanic.release.utilities.FileSearch;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class JavaManifest implements VersionSourceFile {

    private final String MANIFEST_FILE = "manifest.mf";
    private final String VERSION_KEY = "Implementation-Version";

    private final List<File> manifests = new ArrayList<>();

    @Override
    public boolean isPresent() {
        return !manifests.isEmpty();
    }

    public boolean setVersion(File manifest, String versionString) {
        try {
            ManifestEditor editor = new ManifestEditor();
            editor.load(manifest);
            editor.set(VERSION_KEY, versionString);
            editor.save(manifest);
            return true;
        } catch (Exception e) {
        }

        return false;
    }

    @Override
    public List<String> getVersions() {

        ArrayList<String> versions = new ArrayList<>();

        for (File manifest : manifests) {
            try {
                ManifestEditor editor = new ManifestEditor();

                editor.load(manifest);

                String version = editor.get(VERSION_KEY);

                if (version != null) {
                    versions.add(version);
                }
            } catch (Exception e) {
            }
        }

        return versions;
    }

    @Override
    public void setup(DirectoryScannerBundle scanners) {
        
        List<File> presentManifests = new DirectoryScannerBundleExtensions(scanners)
                .getFilesByName(MANIFEST_FILE, StringComparison.COMPARE_CASE_INSENSITIVE);
        
        this.manifests.clear();
        
        this.manifests.addAll(presentManifests);
        
    }

    @Override
    public boolean setVersion(String versionString) {

        boolean success = !this.manifests.isEmpty();

        for (File manifest : manifests) {

            success = success && setVersion(manifest, versionString);
        }
        return success;
    }

    @Override
    public String getName() {
        return "Java Manifest File (manifest.mf)";
    }

}
