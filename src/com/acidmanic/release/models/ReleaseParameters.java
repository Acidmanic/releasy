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
package com.acidmanic.release.models;

import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versions.Change;
import com.acidmanic.release.versions.Version;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class ReleaseParameters {

    private List<Versionable> versionables;
    private Versionable releaser;
    private Version version;
    private int releaseType;
    private Change changes;
    private Runnable preRelease;

    public ReleaseParameters(List<Versionable> versionables, Versionable releaser, Version version, int releaseType, Change changes, Runnable preRelease) {
        this.versionables = versionables;
        this.releaser = releaser;
        this.version = version;
        this.releaseType = releaseType;
        this.changes = changes;
        this.preRelease = preRelease;
    }

    

    public List<Versionable> getVersionables() {
        return versionables;
    }

    public void setVersionables(List<Versionable> versionables) {
        this.versionables = versionables;
    }

    public int getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(int releaseType) {
        this.releaseType = releaseType;
    }

    public Change getChanges() {
        return changes;
    }

    public void setChanges(Change changes) {
        this.changes = changes;
    }

    public Versionable getReleaser() {
        return releaser;
    }

    public void setReleaser(Versionable releaser) {
        this.releaser = releaser;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Runnable getPreRelease() {
        return preRelease;
    }

    public void setPreRelease(Runnable preRelease) {
        this.preRelease = preRelease;
    }

  

}
