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

import com.acidmanic.release.fileeditors.xmlinplace.XmlInPlaceEditor;
import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.versions.Version;
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
public class Maven implements Versionable {

    private boolean mavenPomPresent;
    private File mavenPomFile;

    @Override
    public void setDirectory(File directory) {
        this.mavenPomFile = null;
        this.mavenPomPresent = false;
        File pom = searchForPom(directory);
        if (pom != null && pom.exists()) {
            this.mavenPomFile = pom;
            this.mavenPomPresent = true;
        }
    }

    @Override
    public boolean setVersion(Version version) {
        if (mavenPomPresent) {
            try {
                setMavenProjectVersion(version);
                return true;
            } catch (Exception e) {
                Logger.log("Unable to set Version: " + e.getClass().getSimpleName(), this);
            }
        }
        return false;
    }

    private void setMavenProjectVersion(Version version) throws IOException {
        String mavenFileContent = new String(Files.readAllBytes(this.mavenPomFile.toPath()));
        XmlInPlaceEditor editor = new XmlInPlaceEditor();
        mavenFileContent = editor.setTagContent(new String[]{"project", "version"}, mavenFileContent,
                version.getVersionString());
        Files.write(this.mavenPomFile.toPath(), mavenFileContent.getBytes(), StandardOpenOption.CREATE);
        Logger.log("Maven Project Version set.", this);
    }

    private File searchForPom(File directory) {
        File[] files = directory.listFiles((File dir, String name) -> compareFileNames(name, "pom.xml"));
        if (files.length > 0) {
            return files[0];
        }
        return null;
    }

    private boolean compareFileNames(String name, String pomxml) {
        return name.toLowerCase().compareTo(pomxml.toLowerCase()) == 0;
    }

    @Override
    public boolean isPresent() {
        return mavenPomPresent;
    }

    @Override
    public List<String> getVersions() {
        try {
            String mavenFileContent = new String(Files.readAllBytes(this.mavenPomFile.toPath()));
            XmlInPlaceEditor editor = new XmlInPlaceEditor();
            ArrayList<String> ret = new ArrayList<>();
            ret.add(editor.getTagContent(new String[]{"project", "version"}, mavenFileContent));
            return ret;
        } catch (Exception e) {
        }
        return null;
    }

}
