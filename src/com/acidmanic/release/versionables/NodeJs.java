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

import com.acidmanic.release.fileeditors.JsonEditor;
import com.acidmanic.release.versions.Version;
import com.acidmanic.utilities.FileSearch;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class NodeJs implements Versionable {

    private boolean present;
    private File packageFile;
    private static final String[] VERSION_ADDRESS = {"version"};

    @Override
    public void setup(File directory, int releaseType) {
        packageFile = new FileSearch().search(directory, "package.json");
        if (packageFile != null && packageFile.exists()) {
            this.present = true;
        }
    }

    @Override
    public boolean isPresent() {
        return this.present;
    }

    @Override
    public boolean setVersion(Version version) {
        if (present) {
            try {
                JsonEditor editor = new JsonEditor(packageFile);
                editor.setValue(VERSION_ADDRESS, version.getVersionString());
                return true;
            } catch (Exception e) {
            }
        }
        return false;
    }

    @Override
    public List<String> getVersions() {
        ArrayList<String> ret = new ArrayList<>();
        if (present) {
            try {
                JsonEditor editor = new JsonEditor(packageFile);
                String version = editor.readValue(VERSION_ADDRESS);
                ret.add(version);
            } catch (Exception e) {
            }
        }
        return ret;
    }

}
