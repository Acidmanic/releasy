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

import com.acidmanic.release.projectdirectory.XCodeProjectDirectoryInfo;
import com.acidmanic.release.fileeditors.SpecFileEditor;
import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.versions.Version;
import java.io.File;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Cocoapods implements Versionable {

    private final static String PODSPEC_EXT = ".podspec";

    private boolean present = false;
    private String projectName = null;
    private File specsFile = null;

    private static File getSpecFile(File directory, String projectName) {
        return directory.toPath().resolve(projectName + PODSPEC_EXT)
                .toFile();
    }

    @Override
    public boolean setVersion(Version version) {
        if (this.present) {
            try {
                new SpecFileEditor(this.specsFile)
                        .setVerion(version.getVersionString());
                return true;
            } catch (Exception e) {
                Logger.log("Unable to set Version: " + e.getClass().getSimpleName(), this);
            }
        } else {
            Logger.log("No Cocoapods project found.", this);
        }
        return false;
    }

    @Override
    public void setDirectory(File directory) {
        this.projectName = new XCodeProjectDirectoryInfo().getProjectName(directory);
        this.present = false;
        if (this.projectName != null) {
            this.specsFile = getSpecFile(directory, this.projectName);
            this.present = this.specsFile.exists();
        } else {
            Logger.log("It's not an XCode project directory.", this);
        }
    }

    @Override
    public boolean isPresent() {
        return this.present;
    }

    @Override
    public String getVersion() {
        if (isPresent()) {
            try {
                return new SpecFileEditor(this.specsFile)
                        .getVerion();
            } catch (Exception e) {
            }
        }
        return null;
    }

}
