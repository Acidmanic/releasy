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
import com.acidmanic.release.versions.ReleaseTypes;
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
public class Maven implements Versionable {

    private static final String MAVEN_SNAPSHOT_POSTFIX = "-SNAPSHOT";

    private boolean mavenPomPresent;
    private File mavenPomFile;
    private int releaseType;

    @Override
    public void setup(File directory, int releaseType) {
        this.mavenPomFile = null;
        this.mavenPomPresent = false;
        File pom = new FileSearch().search(directory,"pom.xml");
        if (pom != null && pom.exists()) {
            this.mavenPomFile = pom;
            this.mavenPomPresent = true;
        }
        this.releaseType = releaseType;
    }

    @Override
    public boolean setVersion(Version version) {
        if (mavenPomPresent) {
            try {
                setMavenProjectVersion(version, this.releaseType);
                return true;
            } catch (Exception e) {
                Logger.log("Unable to set Version: " + e.getClass().getSimpleName(), this);
            }
        }
        return false;
    }

    private void setMavenProjectVersion(Version version, int releaseType) throws IOException {
        String mavenFileContent = new String(Files.readAllBytes(this.mavenPomFile.toPath()));
        XmlInPlaceEditor editor = new XmlInPlaceEditor();
        mavenFileContent = editor.setTagContent(new String[]{"project", "version"}, mavenFileContent,
                mavenVersionStringFor(version, releaseType));
        if (this.mavenPomFile.exists()) {
            this.mavenPomFile.delete();
        }
        Files.write(this.mavenPomFile.toPath(), mavenFileContent.getBytes(), StandardOpenOption.CREATE);
        Logger.log("Maven Project Version set.", this);
    }

    @Override
    public boolean isPresent() {
        return mavenPomPresent;
    }

    @Override
    public List<String> getVersions() {
        ArrayList<String> ret = new ArrayList<>();
        try {
            String mavenFileContent = new String(Files.readAllBytes(this.mavenPomFile.toPath()));
            XmlInPlaceEditor editor = new XmlInPlaceEditor();
            String mavenVersion = editor.getTagContent(new String[]{"project", "version"}, mavenFileContent);
            if (mavenVersion.endsWith(MAVEN_SNAPSHOT_POSTFIX)) {
                mavenVersion = mavenVersion.substring(0,
                        mavenVersion.lastIndexOf(MAVEN_SNAPSHOT_POSTFIX));
            }
            ret.add(mavenVersion);
            return ret;
        } catch (Exception e) {
        }
        return ret;
    }

    private String mavenVersionStringFor(Version version, int releaseType) {
        String ret = version.getVersionString();
        if (releaseType != ReleaseTypes.STABLE) {
            ret += MAVEN_SNAPSHOT_POSTFIX;
        }
        return ret;
    }

}
