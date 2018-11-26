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
import com.acidmanic.release.versioning.Version;
import java.io.File;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Cocoapods implements Versionable {

    private final static String PODSPEC_EXT = ".podspec";

    private boolean canSet = false;
    private String projectName = null;
    private File specsFile = null;


    private static File getSpecFile(File directory, String projectName) {
        return directory.toPath().resolve(projectName + PODSPEC_EXT)
                .toFile();
    }

    @Override
    public void setVersion(Version version) {
        if (this.canSet) {
            new SpecFileEditor(this.specsFile)
                    .setVerion(version.getVersionString());
        }else{
            Logger.log("No Cocoapods project found.",this);
        }
    }

    @Override
    public void setDirectory(File directory) {
        this.projectName = new XCodeProjectDirectoryInfo().getProjectName(directory);
        this.canSet = false;
        if (this.projectName != null) {
            this.specsFile = getSpecFile(directory, this.projectName);
            this.canSet = this.specsFile.exists();
        }else{
            Logger.log("It's not an XCode project directory.",this);
        }
    }

    @Override
    public boolean canSetVersion() {
        return this.canSet;
    }

}
