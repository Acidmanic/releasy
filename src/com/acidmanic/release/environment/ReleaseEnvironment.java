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
package com.acidmanic.release.environment;

import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versions.ReleaseTypes;
import com.acidmanic.release.utilities.ClassRegistery;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.acidmanic.release.application.Application;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class ReleaseEnvironment {

    private final File directory;

    public ReleaseEnvironment(File directory) {
        this.directory = directory;
    }

    public ReleaseEnvironment() {
        this.directory = new File(".");
    }

    public List<Versionable> getPresentVersionables() {
        List<Versionable> versionables = ClassRegistery.makeInstance()
                .all(Versionable.class
                );
        List<Versionable> ret = new ArrayList<>();
        for (Versionable v : versionables) {
            v.setup(directory, ReleaseTypes.NIGHTLY);
            if (v.isPresent()) {
                try {
                    ret.add(
                            v.getClass().newInstance()
                    );
                } catch (Exception e) {
                }
            }
        }
        return ret;
    }

    public File getDirectory() {
        return directory;
    }

    public List<String> enumAllVersions() {
        List<Versionable> versionables = getPresentVersionables();
        List<String> ret = retrieveVersions(versionables);
        List<String> releasedVersions = getReleaserVersions();
        ret.addAll(releasedVersions);
        return ret;
    }

    private List<String> retrieveVersions(List<Versionable> versionables) {
        List<String> ret = new ArrayList();
        for (Versionable versionable : versionables) {
            versionable.setup(directory, 0);
            List<String> versions = versionable.getVersions();
            ret.addAll(versions);
        }
        return ret;
    }

    private List<String> getReleaserVersions() {
        try {
            Versionable clone = Application.getReleaser().getClass().newInstance();
            clone.setup(directory, 0);
            return clone.getVersions();
        } catch (Exception e) {
        }
        return new ArrayList<>();
    }

}
