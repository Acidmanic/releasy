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

import com.acidmanic.parse.stringcomparison.StringComparison;
import com.acidmanic.release.fileeditors.xmlinplace.XmlInPlaceEditor;
import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.versions.Version;
import com.acidmanic.utilities.FileSearch;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class NuGet implements Versionable {

    private int releaseType;
    private File specFile;
    private boolean present = false;
    private static final String PACKAGE_SPEC = "Package.nuspec";
    private static final String[] VERSION_ADDRESS = {"package", "metadata", "version"};

    @Override
    public void setup(File directory, int releaseType) {
        this.releaseType = releaseType;
        specFile = new FileSearch().search(directory,
                PACKAGE_SPEC, StringComparison.COMPARE_CASE_INSENSITIVE);
        if (specFile != null) {
            this.present = specFile.exists();
        }
    }

    @Override
    public boolean isPresent() {
        return present;
    }

    @Override
    public boolean setVersion(Version version) {
        try {
            setNuGetProjectVersion(version, releaseType);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    private void setNuGetProjectVersion(Version version, int releaseType) throws IOException {
        String nugetSpecContent = new String(Files.readAllBytes(this.specFile.toPath()));
        XmlInPlaceEditor editor = new XmlInPlaceEditor();
        nugetSpecContent = editor.setTagContent(VERSION_ADDRESS, nugetSpecContent, version.getVersionString());
        if (this.specFile.exists()) {
            this.specFile.delete();
        }
        Files.write(this.specFile.toPath(), nugetSpecContent.getBytes(), StandardOpenOption.CREATE);
        Logger.log("Nuget Project Version set.", this);
    }

    @Override
    public List<String> getVersions() {
        ArrayList<String> ret = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(this.specFile.toPath()));
            XmlInPlaceEditor editor = new XmlInPlaceEditor();
            String version = editor.getTagContent(VERSION_ADDRESS, content);
            
            ret.add(version);
            return ret;
        } catch (Exception e) {
        }
        return ret;
    }

}
