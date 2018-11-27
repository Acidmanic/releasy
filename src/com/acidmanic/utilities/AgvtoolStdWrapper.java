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

import java.util.ArrayList;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class AgvtoolStdWrapper {

    public boolean checkAGV() {
        Bash b = new Bash();
        String command = "agvtool help";
        if (b.commandCanBeRunned(command)) {
            String result = b.syncRun(command);
            return result != null && result.contains("usage")
                    && result.contains("agvtool help")
                    && result.contains("new-version")
                    && result.contains("new-marketing-version");
        }
        return false;
    }

    public void setVersion(String full, String patch) {
        Bash b = new Bash();
        b.syncRun("agvtool -noscm new-version " + patch);
        b.syncRun("agvtool -noscm new-marketing-version \""
                + full + "\"");
    }

    public String getFullVersion() {
        String fullResult = new Bash().syncRun("agvtool what-marketing-version");
        String[] lines = fullResult.split("\\n");
        ArrayList<String> versions = new ArrayList<>();
        for (String line : lines) {
            if (isFullVersionLine(line)) {
                String version = extractVersion(line);
                versions.add(version);
            }
        }

        if (!versions.isEmpty()) {
            return versions.get(0);
        }
        return null;
    }

    private static final String ST_TAG = " of \"";
    private static final String ND_TAG = "\" in \"";

    private boolean isFullVersionLine(String line) {
        return line != null
                && line.startsWith("Found ")
                && line.endsWith("Info.plist\"")
                && line.indexOf(ST_TAG) > 0
                && line.indexOf(ND_TAG) > 0;
    }

    private String extractVersion(String line) {
        int st = line.indexOf(ST_TAG);
        int nd = line.indexOf(ND_TAG);
        return line.substring(st + ST_TAG.length(), nd);

    }
}
