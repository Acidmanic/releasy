/*
 * Copyright (C) 2020 Acidmanic
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
package com.acidmanic.release.versionsources;

import com.acidmanic.release.directoryscanning.DirectoryScannerBundle;
import java.util.List;

/**
 *
 * @author Acidmanic
 */
public interface VersionSourceFile {

    // Perform any action needed before using fuctionalities
    void setup(DirectoryScannerBundle scanners);

    // Returns if there is any instances of this VersoinSourceFile present in PWD
    boolean isPresent();

    // Sets the given versionString in all instances of this VersionSourceFile in PWD
    boolean setVersion(String versionString);

    // Lists all version Strings apeared in all instances of this VersionSourceFile
    List<String> getVersions();

    // Gives the name of the source file versioning. ex: NuGet, Maven, and etc.
    String getName();

}
