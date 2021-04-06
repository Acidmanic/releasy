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
package com.acidmanic.release.versionsources;

import com.acidmanic.parse.stringcomparison.StringComparison;

/**
 *
 * @author Acidmanic
 */
public class NuGetDotnetCore extends XmlVersionSourceFileBase {

    private static final String FILENAME_PATTERN = ".+\\.csproj";
    private static final String[] VERSION_ADDRESS = {"Project", "PropertyGroup", "Version"};
    private static final int COMPARISON = StringComparison.COMPARE_REGEX_MATCH;

    public NuGetDotnetCore() {
        super(FILENAME_PATTERN, VERSION_ADDRESS, COMPARISON);
    }

    @Override
    public String getName() {
        return "NuGet (csproj)";
    }
}
