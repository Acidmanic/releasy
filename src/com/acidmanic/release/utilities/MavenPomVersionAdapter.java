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

import com.acidmanic.release.versions.ReleaseTypes;
import com.acidmanic.release.versions.Version;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class MavenPomVersionAdapter {

    private static final String MAVEN_SNAPSHOT_POSTFIX = "-SNAPSHOT";

    public String pomFileVersionToVersion(String pomVersion) {
        if (pomVersion.endsWith(MAVEN_SNAPSHOT_POSTFIX)) {
            pomVersion = pomVersion.substring(0,
                    pomVersion.lastIndexOf(MAVEN_SNAPSHOT_POSTFIX));
        }
        return pomVersion;
    }

    public String versionToPomFileVersion(Version version, int releaseType) {
        return versionToPomFileVersion(version.getVersionString(), releaseType);
    }

    public String versionToPomFileVersion(String version, int releaseType) {
        if (releaseType != ReleaseTypes.STABLE) {
            version += MAVEN_SNAPSHOT_POSTFIX;
        }
        return version;
    }
}
