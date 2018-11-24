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

import java.io.File;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class XCodeProjectDirectoryInfo {
    
    public static final String XCODE_PROJECT_DIR_EXT = ".xcodeproj";

    public String getProjectName(File directory) {
        File[] files = directory.listFiles((File dir, String name) -> name.endsWith(XCODE_PROJECT_DIR_EXT));
        for (File file : files) {
            if (file.isDirectory()) {
                String name = file.getName();
                return name.substring(0, name.length() - XCODE_PROJECT_DIR_EXT.length());
            }
        }
        return null;
    }
    
}
