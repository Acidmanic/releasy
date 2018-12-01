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
public abstract class XmlSpecFiledVersionable implements Versionable {

    protected int releaseType;
    protected File specFile;
    protected boolean present = false;

    private final String specFileName;
    private final String[] versionAddress;
    private final int filenameComparison;

    public XmlSpecFiledVersionable(String specFileName, String[] versionAddress, int filenameComparison) {
        this.specFileName = specFileName;
        this.versionAddress = versionAddress;
        this.filenameComparison = filenameComparison;
    }

    @Override
    public void setup(File directory, int releaseType) {
        this.releaseType = releaseType;
        specFile = new FileSearch().search(directory,
                specFileName, filenameComparison);
        if (specFile != null) {
            this.present = specFile.exists();
        }
    }

    @Override
    public boolean isPresent() {
        return present;
    }

    protected void setXmlSpecFileVersion(String version) throws IOException {
        String nugetSpecContent = new String(Files.readAllBytes(this.specFile.toPath()));
        XmlInPlaceEditor editor = new XmlInPlaceEditor();
        nugetSpecContent = editor.setTagContent(versionAddress, nugetSpecContent, version);
        if (this.specFile.exists()) {
            this.specFile.delete();
        }
        Files.write(this.specFile.toPath(), nugetSpecContent.getBytes(), StandardOpenOption.CREATE);
        Logger.log(" Project Version set.", this);
    }

    @Override
    public boolean setVersion(Version version) {
        String myName = getClass().getSimpleName();
        try {
            String sVersion = getVersionString(version, releaseType);
            setXmlSpecFileVersion(sVersion);
            Logger.log(myName + " Project Version set.", this);
            return true;
        } catch (Exception e) {
            Logger.log("Unable to set Version: " + myName, this);
        }
        return false;
    }

    @Override
    public List<String> getVersions() {
        ArrayList<String> ret = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(this.specFile.toPath()));
            XmlInPlaceEditor editor = new XmlInPlaceEditor();
            String version = editor.getTagContent(versionAddress, content);
            ret.add(version);
            return ret;
        } catch (Exception e) {
        }
        return ret;
    }

    private String getVersionString(Version version, int releaseType) {
        return version.getVersionString();
    }

}
