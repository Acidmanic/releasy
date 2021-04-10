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
package com.acidmanic.release.versionables;

import com.acidmanic.release.fileeditors.ManifestEditor;
import com.acidmanic.release.versions.Version;
import com.acidmanic.release.utilities.FileSearch;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
@Deprecated
public class JavaManifest implements Versionable {

    private final String MANIFEST_FILE = "manifest.mf";
    private final String VERSION_KEY = "Implementation-Version";

    private boolean present;
    private File manifest;

    @Override
    public void setup(File directory, int releaseType) {
        manifest = new FileSearch().search(directory, MANIFEST_FILE, releaseType);
        if (manifest != null) {
            present = manifest.exists();
        }
    }

    @Override
    public boolean isPresent() {
        return present;
    }

    @Override
    public boolean setVersion(Version version) {
        if (present) {
            ManifestEditor editor = new ManifestEditor();
            editor.load(manifest);
            editor.set(VERSION_KEY, version.getVersionString());
            editor.save(manifest);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getVersions() {
        ArrayList<String> ret = new ArrayList<>();
        try {
            ManifestEditor editor = new ManifestEditor();
            editor.load(manifest);
            String version = editor.get(VERSION_KEY);
            if (version != null) {
                ret.add(version);
            }
        } catch (Exception e) {
        }
        return ret;
    }

}
