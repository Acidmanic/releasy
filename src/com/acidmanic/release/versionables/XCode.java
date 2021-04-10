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
import com.acidmanic.release.versions.Version;
import com.acidmanic.release.utilities.AgvtoolStdWrapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
@Deprecated
public class XCode implements Versionable {

    private String projectName = null;
    private boolean isXcodeProject = false;
    private boolean isAGVPresent = false;

    @Override
    public void setup(File directory, int releaseType) {
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
            }
        }
        return false;
    }

    private void setVersionOnXCode(Version version) {
        String full;
        
        int patch;
        
        patch = version.getNumericPatch();
        
        if (patch == Version.VERSION_VALUE_NOT_SUPPORTED){
            patch = 0;
        }
        
        full = version.getVersionString();
        
        new AgvtoolStdWrapper().setVersion(full, Integer.toString(patch));
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
        return new ArrayList<>();

    }

}
