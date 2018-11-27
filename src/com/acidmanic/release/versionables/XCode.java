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
import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.versions.SemanticVersion;
import com.acidmanic.release.versions.Version;
import com.acidmanic.utilities.AgvtoolStdWrapper;
import com.acidmanic.utilities.Bash;
import java.io.File;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class XCode implements Versionable {

    private String projectName = null;
    private boolean isXcodeProject = false;
    private boolean isAGVPresent = false;

    @Override
    public void setDirectory(File directory) {
        this.projectName = new XCodeProjectDirectoryInfo().getProjectName(directory);
        this.isXcodeProject = this.projectName != null;
        this.isAGVPresent = new AgvtoolStdWrapper().checkAGV();
    }

    @Override
    public boolean setVersion(Version version) {
        if (isPresent()) {
            if (isAGVPresent) {
                try {
                    setVersionOnXCode(version);
                    return true;
                } catch (Exception e) {
                    Logger.log("Unable to set Version: " + e.getClass().getSimpleName(), this);
                }
            } else {
                Logger.log("Can not set the version.", this);
                Logger.log("Apple-generic versioning tool for Xcode projects is not available.", this);
            }
        }
        return false;
    }

    private void setVersionOnXCode(Version version) {
        String full, patch;
        if (version instanceof SemanticVersion) {
            patch = Integer.toString(((SemanticVersion) version).getPatch());
        } else {
            patch = "0";
            Logger.log("WARNING: "
                    + "Patch version defaulted to zero due to choosed version format.", this);
        }
        full = version.getVersionString();
        new AgvtoolStdWrapper().setVersion(patch, full);
    }

    @Override
    public boolean isPresent() {
        return isXcodeProject;
    }

    @Override
    public List<String> getVersions() {
        if (isXcodeProject && isAGVPresent) {
            return new AgvtoolStdWrapper().getFullVersions();
        }
        return null;

    }

}
