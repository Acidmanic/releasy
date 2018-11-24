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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

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

    private String checkReplaceVersion(String line, String version) {
        String linesig = line.replaceAll("\\s", "").toLowerCase();
        linesig = linesig.replaceAll(".+\\.version", "version");
        if (linesig.startsWith("version=")) {
            int eqStart = line.indexOf("=");
            String ret = line.substring(0, eqStart + 1);
            ret += " '" + version + "'";
            return ret;
        }
        return line;
    }

}
