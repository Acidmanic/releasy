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
package com.acidmanic.utilities;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class AgvTestBashFactory implements BashFactory {

    public static final String FULL_VERSIONS = "1.1.1-release";
    public static final int FULL_VERSIONS_COUNT = 2;

    @Override
    public Bash make() {
        return new BashAvgMock();
    }

    private class BashAvgMock extends Bash {

        private static final String CM_HELP = "agvtool help";
        private static final String CM_FULL_VERS = "agvtool what-marketing-version";

        private static final String RES_HELP = "agvtool - Apple-generic versioning tool for Xcode projects\n"
                + "  usage:\n"
                + "    agvtool help\n"
                + "    agvtool what-version | vers [-terse]\n"
                + "    agvtool [-noscm | -usecvs | -usesvn] next-version | bump [-all]\n"
                + "    agvtool [-noscm | -usecvs | -usesvn] new-version [-all] <versNum>\n"
                + "    agvtool [-noscm | -usecvs | -usesvn] tag [-force | -F] [-noupdatecheck | -Q] [-baseurlfortag]\n"
                + "    agvtool what-marketing-version | mvers [-terse | -terse1]\n"
                + "    agvtool [-noscm | -usecvs | -usesvn] new-marketing-version <versString>\n"
                + "\n"
                + "\n"
                + "For more information type \"man agvtool\".";

        private static final String RES_FULL_VERS = "No marketing version number (CFBundleShortVersionString) found for Jambase targets.\n"
                + "\n"
                + "Looking for marketing version in native targets...\n"
                + "Looking for marketing version (CFBundleShortVersionString) in native targets...\n"
                + "\n"
                + "Found CFBundleShortVersionString of \"1.1.1-release\" in \"NamingConventions.xcodeproj/../NamingConventions/Info.plist\" \n"
                + "Found CFBundleShortVersionString of \"1.1.1-release\" in \"NamingConventions.xcodeproj/../NamingConventionsTests/Info.plist\"";

        @Override
        public String syncRun(String command) {
            if (CM_HELP.compareTo(command) == 0) {
                return RES_HELP;
            }
            if (CM_FULL_VERS.compareTo(command) == 0) {
                return RES_FULL_VERS;
            }

            return super.syncRun(command);
        }

        @Override
        public boolean commandCanBeRunned(String command) {
            if (CM_HELP.compareTo(command) == 0) {
                return true;
            }
            return super.commandCanBeRunned(command);
        }

    }
}
