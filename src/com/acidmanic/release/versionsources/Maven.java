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
package com.acidmanic.release.versionsources;

import com.acidmanic.parse.stringcomparison.StringComparison;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Maven extends XmlVersionSourceFileBase {

    private static final String MAVEN_SPEC_FILE = "pom.xml";
    private static final String[] VERSION_ADDRESS = {"project", "version"};
    private static final int COMPARISON = StringComparison.COMPARE_CASE_SENSITIVE;

    public Maven() {
        super(MAVEN_SPEC_FILE, VERSION_ADDRESS, COMPARISON);
    }

    @Override
    public String getName() {
        return "Maven (pom.xml)";
    }

}
