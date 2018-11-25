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
import com.acidmanic.release.versioning.Version;
import com.acidmanic.release.versioning.Versionable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

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
    public boolean canSetVersion() {
        return mavenPomPresent;
    }

    @Override
    public void setVersion(Version version) {
        try {

            String mavenFileContent = new String(Files.readAllBytes(this.mavenPomFile.toPath()));
            XmlInPlaceEditor editor = new XmlInPlaceEditor();
            mavenFileContent = editor.setTagContent(new String[]{"project", "version"}, mavenFileContent,
                    version.getVersionString());
            Files.write(this.mavenPomFile.toPath(), mavenFileContent.getBytes(), StandardOpenOption.CREATE);
            Logger.log("Maven Project Version set.", this);
        } catch (Exception e) {
            Logger.log("ERROR: Unable to set maven project version.", this);
            Logger.log("ERROR: " + e.getClass().getSimpleName(), this);

        }

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

   

}
