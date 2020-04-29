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
public class SemanticVersion implements Version {

    private int major;
    private int minor;
    private int patch;
    private String identifiers;

    private boolean hasIdentifiers() {
        return this.identifiers != null && this.identifiers.length() > 0;
    }

    public SemanticVersion() {
        major = 1;
        minor = 0;
        patch = 0;
    }

    public SemanticVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.identifiers = null;
    }

    public SemanticVersion(int major, int minor, int patch, String identifiers) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.identifiers = identifiers;
    }

    @Override
    public int getNumericMajor() {
        return major;
    }

    @Override
    public void setNumericMajor(int major) {
        this.major = major;
    }

    @Override
    public int getNumericMinor() {
        return minor;
    }

    @Override
    public void setNumericMinor(int minor) {
        this.minor = minor;
    }

    @Override
    public int getNumericPatch() {
        return patch;
    }

    @Override
    public void setNumericPatch(int patch) {
        this.patch = patch;
    }

    public String getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(String identifiers) {
        this.identifiers = identifiers;
    }

    @Override
    public String getVersionString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.major).append(".")
                .append(this.minor).append(".")
                .append(this.patch);
        if (hasIdentifiers()) {
            sb.append("-").append(this.identifiers);
        }
        return sb.toString();
    }

    @Override
    public boolean tryParse(String versionString) {
        String[] parts = versionString.split("\\.");
        if (parts.length != 3) {
            return false;
        }
        try {
            this.major = Integer.parseInt(parts[0]);
            this.minor = Integer.parseInt(parts[1]);
            String sPatch, id = "";
            int st = parts[2].indexOf("-");
            if (st < 0) {
                sPatch = parts[2];
            } else if (st == 0) {
                sPatch = "0";
                id = parts[2];
            } else {
                sPatch = parts[2].substring(0, st);
                id = parts[2].substring(st + 1, parts[2].length());
            }
            this.patch = Integer.parseInt(sPatch);
            this.identifiers = id;
            return true;
        } catch (Exception e) {
        }

        return false;
    }

    @Override
    public int compare(Version v) {
        if (v instanceof SemanticVersion) {
            return this.compare((SemanticVersion) v);
        }
        return 0;
    }

    public int compare(SemanticVersion v) {
        int maj = compareInt(this.major, v.major);
        if (maj != 0) {
            return maj;
        }
        int min = compareInt(this.minor, v.minor);
        if (min != 0) {
            return min;
        }
        return compareInt(this.patch, v.patch);
    }

    private int compareInt(int value1, int value2) {
        if (value1 == value2) {
            return 0;
        }
        return value1 > value2 ? -1 : 1;
    }
}
