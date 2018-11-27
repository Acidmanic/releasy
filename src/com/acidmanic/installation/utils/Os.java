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
package com.acidmanic.installation.utils;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Os {

    private final String osName;

    private final boolean windows;
    private final boolean mac;
    private final boolean linux;
    private final boolean aix;
    private final boolean solaris;

    private final boolean unix;

    public Os() {

        this.osName = System.getProperty("os.name").toLowerCase();

        this.windows = this.osName.contains("win");

        this.mac = this.osName.contains("mac");
        
        this.linux = this.osName.contains("nux");
        
        this.aix = 0 < this.osName.indexOf("aix");
        
        this.solaris = 0 < this.osName.indexOf("sunos");

        this.unix = this.osName.contains("nix")
                || this.linux || this.solaris
                || this.mac || this.aix;
    }

    public String getOsName() {
        return osName;
    }

    public boolean isWindows() {
        return windows;
    }

    public boolean isMac() {
        return mac;
    }

    public boolean isLinux() {
        return linux;
    }

    public boolean isAix() {
        return aix;
    }

    public boolean isSolaris() {
        return solaris;
    }

    public boolean isUnix() {
        return unix;
    }

}
