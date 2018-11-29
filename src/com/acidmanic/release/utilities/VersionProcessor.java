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
package com.acidmanic.release.utilities;

import com.acidmanic.release.versions.Change;
import com.acidmanic.release.versions.Version;
import com.acidmanic.release.versions.VersionFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class VersionProcessor {

    private final VersionFactory factory;
    private final Version blankSample;

    public VersionProcessor(VersionFactory factory) {
        this.factory = factory;
        this.blankSample = factory.blank();
    }

    protected boolean conforms(Version version) {
        return version.getClass().isInstance(this.blankSample);
    }

    public Version getLatest(List<Version> all) {

        Version maximum = this.blankSample;

        for (Version version : all) {
            if (conforms(version)) {
                if (version.compare(maximum) < 0) {
                    maximum = version;
                }
            }
        }

        return maximum;
    }

    public Version generateVersion(List<Version> all,
            Change changes, int releaseType) {
        Version base = getLatest(all);
        return this.factory.make(base, changes, releaseType);
    }

    public Version generateVersionFromStrings(List<String> all,
            Change changes, int releaseType) {
        List<Version> versions = toVersionList(all);
        return generateVersion(versions, changes, releaseType);
    }

    public List<Version> toVersionList(List<String> versions) {
        List<Version> ret = new ArrayList<>();
        for (String version : versions) {
            Version v = this.factory.blank();
            if (v.tryParse(version)) {
                ret.add(v);
            }
        }
        return ret;
    }
}
