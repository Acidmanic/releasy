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
package com.acidmanic.release.utilities;

import com.acidmanic.utilities.Bash;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class AgvtoolStdWrapper {

    private final BashFactory bashFactory;

    public AgvtoolStdWrapper() {
        this.bashFactory = new DefaultBashFactory();
    }

    public AgvtoolStdWrapper(BashFactory bashFactory) {
        this.bashFactory = bashFactory;
    }

    public boolean checkAGV() {
        Bash b = bashFactory.make();
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
        Bash b = bashFactory.make();
        b.syncRun("agvtool -noscm new-version " + patch);
        b.syncRun("agvtool -noscm new-marketing-version \""
                + full + "\"");
    }

    public List<String> getFullVersions() {
        String fullResult = bashFactory.make().syncRun("agvtool what-marketing-version");
        String[] lines = fullResult.split("\\n");
        ArrayList<String> versions = new ArrayList<>();
        for (String line : lines) {
            line = line.trim();
            if (isFullVersionLine(line)) {
                String version = extractVersion(line);
                versions.add(version);
            }
        }
        return versions;
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
