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

import com.acidmanic.release.fileeditors.SpecFileEditor;
import com.acidmanic.release.versioning.Version;
import com.acidmanic.release.versioning.Versionable;
import java.io.File;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Cocoapods implements Versionable {

    private final static String XCODE_PROJECT_DIR_EXT = ".xcodeproj";
    private final static String PODSPEC_EXT = ".podspec";

    private boolean presented = false;
    private String projectName = null;
    private File specsFile = null;

    private String getProjectName(File directory) {
        File[] files = directory.listFiles((File dir, String name) -> name.endsWith(XCODE_PROJECT_DIR_EXT));
        for (File file : files) {
            if (file.isDirectory()) {
                String name = file.getName();
                return name.substring(0, name.length()
                        - XCODE_PROJECT_DIR_EXT.length());
            }
        }
        return null;
    }

    private static File getSpecFile(File directory, String projectName) {
        return directory.toPath().resolve(projectName + PODSPEC_EXT)
                .toFile();
    }

    @Override
    public void setVersion(Version version) {
        if (this.presented) {
            new SpecFileEditor(this.specsFile)
                    .setVerion(version.getVersionString());
        }
    }

    @Override
    public void setDirectory(File directory) {
        this.projectName = getProjectName(directory);
        this.presented = false;
        if (this.projectName != null) {
            this.specsFile = getSpecFile(directory, this.projectName);
            this.presented = this.specsFile.exists();
        }
    }

    @Override
    public boolean isPresentedAt() {
        return this.presented;
    }

}
