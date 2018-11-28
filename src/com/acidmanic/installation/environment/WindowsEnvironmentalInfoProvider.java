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
package com.acidmanic.installation.environment;

import java.io.File;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class WindowsEnvironmentalInfoProvider extends EnvironmentalInfoProviderBase {

    private File getSystemFile(String name) {
        String path = System.getenv(name);
        if (path == null) {
            System.out.println("Unable to find " + name + ".");
        } else {
            return new File(path);
        }
        return null;
    }

    @Override
    protected File executableBinariesDirectory() {
        return getSystemFile("windir");
    }

    @Override
    protected File applicationsDirectory() {
        return getSystemFile("ProgramW6432");
    }

}
