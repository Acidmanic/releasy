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
package com.acidmanic.release.versions;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class SemanticVersionFactory implements VersionFactory<SemanticVersion> {

    @Override
    public SemanticVersion make(SemanticVersion base, Change changes, int releaseType) {
        if (changes.changeDesign) {
            return new SemanticVersion(base.getMajor() + 1, 0, 0);
        }
        int minor = base.getMinor();
        int patch = base.getPatch();
        if (changes.addFeature) {
            minor += 1;
        }
        if (changes.fixBugs) {
            patch += 1;
        }
        String identifier = getIdentifier(releaseType);
        if (identifier == null) {
            return new SemanticVersion(base.getMajor(), minor, patch);
        }
        return new SemanticVersion(base.getMajor(), minor, patch, identifier);
    }

    private String getIdentifier(int releaseType) {
        switch (releaseType) {
            case ReleaseTypes.ALPHA:
                return "alpha";
            case ReleaseTypes.BETA:
                return "beta";
            case ReleaseTypes.RELEASE_CANDIDATE:
                return "rc";
        }
        return null;
    }

    @Override
    public SemanticVersion blank() {
        return new SemanticVersion(0,0,0,"");
    }

}
