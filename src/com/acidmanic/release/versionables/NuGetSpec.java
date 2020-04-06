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
package com.acidmanic.release.versionables;

import com.acidmanic.parse.stringcomparison.StringComparison;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class NuGetSpec extends XmlSpecFiledVersionable {

    private static final String PACKAGE_SPEC = "Package.nuspec";
    private static final String[] VERSION_ADDRESS = {"package", "metadata", "version"};
    private static final int COMPARISON = StringComparison.COMPARE_CASE_SENSITIVE;

    public NuGetSpec() {
        super(PACKAGE_SPEC, VERSION_ADDRESS, COMPARISON);
    }

}
