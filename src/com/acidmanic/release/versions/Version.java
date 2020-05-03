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
@Deprecated
public interface Version {

    public static final int VERSION_VALUE_NOT_SUPPORTED = -1;

    String getVersionString();

    public boolean tryParse(String versionString);

    public int compare(Version v);

    public int getNumericPatch();

    public int getNumericMajor();

    public int getNumericMinor();

    public void setNumericPatch(int value);

    public void setNumericMajor(int value);

    public void setNumericMinor(int value);

    public static final Version NULL = new Version() {
        @Override
        public String getVersionString() {
            return "0.0";
        }

        @Override
        public boolean tryParse(String versionString) {
            return false;
        }

        @Override
        public int compare(Version v) {
            return -1;
        }

        @Override
        public int getNumericPatch() {
            return VERSION_VALUE_NOT_SUPPORTED;
        }

        @Override
        public int getNumericMajor() {
            return VERSION_VALUE_NOT_SUPPORTED;
        }

        @Override
        public int getNumericMinor() {
            return VERSION_VALUE_NOT_SUPPORTED;
        }

        @Override
        public void setNumericPatch(int value) {
        }

        @Override
        public void setNumericMajor(int value) {
        }

        @Override
        public void setNumericMinor(int value) {
        }
    };
}
