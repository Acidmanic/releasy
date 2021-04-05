/*
 * Copyright (C) 2020 diego
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
package com.acidmanic.release.gitbump;

import com.acidmanic.release.commands.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.versions.standard.VersionStandard;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author diego
 */
public class Context {

    private final File directory;

    private final String remote;

    private final HashMap<String, Boolean> executedStepsStatus;

    private final ReleaseWorkspace workspace;

    private final VersionStandard standard;

    public Context(File directory, String remote, ReleaseWorkspace workspace, VersionStandard standard) {
        this.directory = directory;
        this.remote = remote;
        this.workspace = workspace;
        this.standard = standard;
        this.executedStepsStatus = new HashMap<>();
    }

    public void updateExecutionStatus(String id, boolean status) {

        if (this.executedStepsStatus.containsKey(id)) {

            this.executedStepsStatus.remove(id);
        }
        this.executedStepsStatus.put(id, status);
    }

    public boolean getExecutionStatus(String id) {

        if (this.executedStepsStatus.containsKey(id)) {

            return this.executedStepsStatus.get(id);
        }
        return false;
    }

    public File getDirectory() {
        return directory;
    }

    public String getRemote() {
        return remote;
    }

    public ReleaseWorkspace getWorkspace() {
        return workspace;
    }

    public VersionStandard getStandard() {
        return standard;
    }
}
