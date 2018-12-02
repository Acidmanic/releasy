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
package com.acidmanic.release.readmeupdate.updaters;

import com.acidmanic.parse.QuotationParser;
import com.acidmanic.parse.indexbased.IndexBasedParser;
import com.acidmanic.parse.indexbased.SubString;
import com.acidmanic.release.versions.Version;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class CocoapodReadmeUpdater implements ReadmeUpdater {

    private class Cocoa {

        public String name;
        public String versionOperator;
        public String version;
        public char quote;
    }

    @Override
    public String process(String readme, Version version, int releaseType) {

        String[] lines = readme.split("\n");
        StringBuilder sb = new StringBuilder();
        String sVer = version.getVersionString();
        String sep = "";
        for (String line : lines) {
            Cocoa cocoa = readAsCocoa(line);
            if (cocoa != null) {
                line = setVersion(line, cocoa, sVer);
            }
            sb.append(sep).append(line);
            sep = "\n";
        }

        return sb.toString();
    }

    private Cocoa readAsCocoa(String line) {
        line = line.trim();
        QuotationParser q = new QuotationParser();
        if (line.startsWith("pod")) {
            line = line.substring(3, line.length()).trim();
            String[] segments = line.split(",");
            if (segments.length == 2) {
                if (q.isQuotedValues(segments[0]) && q.isQuotedValues(segments[1])) {
                    String[] versionParts = q.unQoute(segments[1]).split("\\s");
                    if (versionParts.length > 0) {
                        Cocoa ret = new Cocoa();
                        ret.name = q.unQoute(segments[0]);
                        ret.version = versionParts[versionParts.length - 1];
                        if (versionParts.length == 2) {
                            ret.versionOperator = versionParts[0];
                        }
                        ret.quote = segments[0].charAt(0);
                        return ret;
                    }
                }
            }
        }
        return null;
    }

    private String setVersion(String line, Cocoa cocoa, String sVer) {
        int st = line.lastIndexOf(cocoa.version);
        SubString sub = new SubString(st, st + cocoa.version.length());
        return new IndexBasedParser().replace(line, sub, sVer);
    }
}
