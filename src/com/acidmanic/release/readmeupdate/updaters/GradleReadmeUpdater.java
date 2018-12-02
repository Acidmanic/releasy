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
import com.acidmanic.release.versions.Version;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class GradleReadmeUpdater extends MavenInfoProvider implements ReadmeUpdater {

    private class GradleProfile {

        public String tag;
        public String groupId;
        public String artifactId;
        public String version;
        public String command;
        public char quote;
    }

    @Override
    public String process(String readme, Version version, int releaseType) {
        provideMavenIinfo();

        if (isMaven) {
            String sVers = version.getVersionString();
            String[] lines = readme.split("\n");
            StringBuilder sb = new StringBuilder();
            String sep = "";
            for (String line : lines) {
                GradleProfile grad = readGradleProfile(line);
                if (grad != null) {
                    line = replaceVersionInGradleLine(grad, line, sVers);
                }
                sb.append(sep).append(line);
                sep = "\n";
            }
            readme = sb.toString();
        }

        return readme;
    }

    private GradleProfile readGradleProfile(String line) {
        String lineData = line.trim();
        String[] outerparts = lineData.split("\\s");
        if (outerparts.length > 1) {
            if (isGradleDependencyCommand(outerparts[0])) {
                QuotationParser q = new QuotationParser();
                if (q.isQuotedValues(outerparts[1])) {
                    String data = q.unQoute(outerparts[1]);
                    String[] segments = data.split(":");
                    if (segments.length > 2) {
                        GradleProfile ret = new GradleProfile();
                        ret.groupId = segments[0];
                        ret.artifactId = segments[1];
                        ret.tag = "compile";
                        ret.version = segments[2];
                        ret.command = outerparts[0];
                        ret.quote = outerparts[1].charAt(0);
                        return ret;
                    }
                }
            }
        }

        return null;
    }

    private String replaceVersionInGradleLine(GradleProfile grad, String line, String sVers) {
        int st = line.lastIndexOf(":");
        String ret = line.substring(0, st) + ":" + sVers;
        st = line.lastIndexOf(grad.quote);
        ret += line.substring(st, line.length());
        return ret;
    }

    private boolean isGradleDependencyCommand(String lineStart) {
        return "compile".compareTo(lineStart) == 0
                || "implementation".compareTo(lineStart) == 0;
    }

}
