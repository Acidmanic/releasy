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

import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.versioning.SemanticVersion;
import com.acidmanic.release.versioning.Version;
import com.acidmanic.release.versioning.Versionable;
import com.acidmanic.utilities.Bash;
import java.io.File;

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
        this.isAGVPresent = checkAGV();
    }

    @Override
    public boolean canSetVersion() {
        return isXcodeProject && isAGVPresent;
    }

    @Override
    public void setVersion(Version version) {
        if (canSetVersion()) {
            Bash b = new Bash();
            if (version instanceof SemanticVersion) {
                b.syncRun("agvtool -noscm new-version "
                        + ((SemanticVersion) version).getPatch());
            } else {
                b.syncRun("agvtool -noscm new-version 0");
                Logger.log("WARNING: "
                        + "Patch version defaulted to zero due to choosed version format.", this);
            }
            b.syncRun("agvtool -noscm new-marketing-version \""
                    + version.getVersionString()+ "\"") ;
        } else {
            Logger.log("Can not set the version.", this);
            if (!isXcodeProject) {
                Logger.log("This directory is not an xCodeProject Directory.", this);
            }
            if (!isAGVPresent) {
                Logger.log("Apple-generic versioning tool for Xcode projects is not available.", this);
            }
        }
    }

    private boolean checkAGV() {
        Bash b = new Bash();
        String command = "agvtool help";
        if (b.commandCanBeRunned(command)) {
            String result = b.syncRun(command);
            return result != null && result.contains("usage")
                    && result.contains("agvtool help")
                    && result.contains("new-version")
                    && result.contains("new-marketing-version");
        }
        return false;
    }

}
