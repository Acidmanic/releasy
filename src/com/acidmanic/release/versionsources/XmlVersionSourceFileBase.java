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
package com.acidmanic.release.versionsources;

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.parse.stringcomparison.StringComparisionFactory;
import com.acidmanic.parse.stringcomparison.StringComparison;
import com.acidmanic.release.commands.directoryscanning.DirectoryScannerBundle;
import com.acidmanic.release.fileeditors.inplaceediting.XmlInPlaceEditor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acidmanic
 */
public abstract class XmlVersionSourceFileBase extends VersionSourceFileBase {

    private final String fileNamePattern;
    private final String[] versionNodeAddress;
    private final int fileNameComparison;

    private List<File> versionFiles;

    public XmlVersionSourceFileBase(String fileNamePattern, String[] versionNodeAddress, int fileNameComparison) {
        this.fileNamePattern = fileNamePattern;
        this.versionNodeAddress = versionNodeAddress;
        this.fileNameComparison = fileNameComparison;

    }

    @Override
    public void setup(DirectoryScannerBundle scanners) {

        this.versionFiles = new ArrayList<>();

        StringComparison comparison = new StringComparisionFactory().make(this.fileNameComparison);
        
        scanners.scan(file -> comparison.areEqual(fileNamePattern, file.getName()),
                file -> this.versionFiles.add(file));
    }

    @Override
    public boolean isPresent() {
        return !this.versionFiles.isEmpty();
    }

    @Override
    public boolean setVersion(String versionString) {

        boolean ret = true;

        for (File versionFile : this.versionFiles) {

            ret &= setVersion(versionFile, versionString);
        }

        return ret;
    }

    @Override
    public List<String> getVersions() {

        ArrayList<String> ret = new ArrayList<>();

        for (File versionFile : this.versionFiles) {

            String version = readVersion(versionFile);

            if (version != null) {

                ret.add(version);
            }
        }
        return ret;
    }

    private String readVersion(File versionFile) {

        String content = new FileIOHelper().tryReadAllText(versionFile);

        XmlInPlaceEditor editor = new XmlInPlaceEditor();

        String versionString = editor.getTagContent(versionNodeAddress, content);

        return versionString;
    }

    private boolean setVersion(File versionFile, String versionString) {

        String content = new FileIOHelper().tryReadAllText(versionFile);

        XmlInPlaceEditor editor = new XmlInPlaceEditor();

        content = editor.setTagContent(versionNodeAddress, content, versionString);

        if (versionFile.exists()) {

            versionFile.delete();
        }

        new FileIOHelper().tryWriteAll(versionFile, content);

        return true;
    }

}
