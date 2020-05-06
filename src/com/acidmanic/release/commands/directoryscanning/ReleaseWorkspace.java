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
package com.acidmanic.release.commands.directoryscanning;

import java.io.File;

/**
 *
 * @author Acidmanic
 */
public class ReleaseWorkspace {
    
    private DirectoryScannerBundle versionFilesScanner;
    
    private File sourceControlRoot;

    public ReleaseWorkspace() {
    }

    public ReleaseWorkspace(DirectoryScannerBundle versionFilesScanner, File sourceControlRoot) {
        this.versionFilesScanner = versionFilesScanner;
        this.sourceControlRoot = sourceControlRoot;
    }

    public DirectoryScannerBundle getVersionFilesScanner() {
        return versionFilesScanner;
    }

    public void setVersionFilesScanner(DirectoryScannerBundle versionFilesScanner) {
        this.versionFilesScanner = versionFilesScanner;
    }

    public File getSourceControlRoot() {
        return sourceControlRoot;
    }

    public void setSourceControlRoot(File sourceControlRoot) {
        this.sourceControlRoot = sourceControlRoot;
    }
    
    
    
}
