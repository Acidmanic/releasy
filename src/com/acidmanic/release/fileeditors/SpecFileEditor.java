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
package com.acidmanic.release.fileeditors;

import com.acidmanic.release.versions.Version;
import com.acidmanic.utilities.StringParseHelper;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import jdk.nashorn.internal.runtime.regexp.RegExp;
import jdk.nashorn.internal.runtime.regexp.RegExpFactory;
import jdk.nashorn.internal.runtime.regexp.RegExpResult;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class SpecFileEditor {

    private final File specFile;

    public SpecFileEditor(File specsFile) {
        this.specFile = specsFile;
    }

    public void setVerion(String version) {
        if (this.specFile.exists()) {
            try {
                StringBuilder sb = new StringBuilder();
                List<String> lines = Files.readAllLines(this.specFile.toPath());
                String sep = "";
                for (String line : lines) {
                    line = checkReplaceVersion(line, version);
                    sb.append(sep).append(line);
                    sep = "\n";
                }
                Files.write(this.specFile.toPath(), sb.toString().getBytes(),
                        StandardOpenOption.WRITE);
            } catch (Exception e) {
            }
        }
    }

    private boolean isVersionLine(String line) {
        String linesig = line.replaceAll("\\s", "").toLowerCase();
        linesig = linesig.replaceAll(".+\\.version", "version");
        return linesig.startsWith("version=");
    }

    private String checkReplaceVersion(String line, String version) {
        if (isVersionLine(line)) {
            int eqStart = line.indexOf("=");
            String ret = line.substring(0, eqStart + 1);
            ret += " '" + version + "'";
            return ret;
        }
        return line;
    }

    public String getVerion() {
        StringParseHelper helper = new StringParseHelper();
        if (this.specFile.exists()) {
            try {
                List<String> lines = Files.readAllLines(this.specFile.toPath());
                for (String line : lines) {
                    if (isVersionLine(line)) {
                        String[] parts = line.split("\\s");
                        boolean isRight = false;
                        for (String part : parts) {
                            if (!isRight) {
                                if ("=".compareTo(part) == 0) {
                                    isRight = true;
                                }
                            } else if (helper.isQuotedValues(part)) {
                                return helper.unQoute(part);
                            }
                        }

                    }
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

   

}
