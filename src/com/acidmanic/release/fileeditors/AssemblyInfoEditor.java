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

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.parse.QuotationParser;
import com.acidmanic.utilities.Plus;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class AssemblyInfoEditor {

    private static final String ASSEMBLY_REX = "\\s*assembly\\s*\\:\\s*";
    private static final String RECORD_REX = "\\[" + ASSEMBLY_REX + "(\\w|_)(\\w|\\d|_)*\\s*\\(\\s*\".+\"\\s*\\)\\s*\\]";
    private static final String PARAM_KEY = "<KEY>";
    private static final String PARAM_VALUE = "<VALUE>";
    private static final String RECORD_REX_PARAM_KEY
            = "\\[" + ASSEMBLY_REX + PARAM_KEY + "\\(\\s*\".+\"\\s*\\)\\s*\\]";
    private static final String RECORD_REX_PARAM_KEY_VALUE
            = "\\[" + ASSEMBLY_REX + PARAM_KEY + "\\(\\s*\"" + PARAM_VALUE + "\"\\s*\\)\\s*\\]";

    public HashMap<String, String> loadFile(File file) {
        HashMap<String, String> ret = new HashMap<>();
        String content = new FileIOHelper().tryReadAllText(file);
        if (content != null && !content.isEmpty()) {
            String lines[] = content.split("\\n");
            for (String line : lines) {
                if (isAssembly(line)) {
                    Plus<String, String> nameValue = parseRecord(line);
                    if (ret.containsKey(nameValue.get())) {
                        ret.remove(nameValue.get());
                    }
                    ret.put(nameValue.get(), nameValue.getExtera());
                }
            }
        }
        return ret;
    }

    public String readValue(File file, String key) {
        String content = new FileIOHelper().tryReadAllText(file);
        if (content != null && !content.isEmpty()) {
            String[] lines = content.split("\n");
            String regEx = RECORD_REX_PARAM_KEY.replace(PARAM_KEY, key);
            for (String line : lines) {
                if (line.trim().matches(regEx)) {
                    return parseRecord(line).getExtera();
                }
            }
        }
        return null;
    }

    public void writeValue(File file, String key, String value) {
        String content = new FileIOHelper().tryReadAllText(file);
        StringBuilder sb = new StringBuilder();
        String sep = "";
        if (content != null && !content.isEmpty()) {
            String[] lines = content.split("\n");
            String regEx = RECORD_REX_PARAM_KEY.replace(PARAM_KEY, key);
            for (String line : lines) {
                if (line.trim().matches(regEx)) {
                    line = getRecordFor(key, value);
                }
                sb.append(sep).append(line);
                sep = "\n";
            }
            new FileIOHelper().tryWriteAll(file, sb.toString());
        }
    }

    public void saveFile(HashMap<String, String> data, File file) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (String key : data.keySet()) {
            sb.append(sep).append(getRecordFor(key, data.get(key)));
            sep = "\n";
        }
        new FileIOHelper().tryWriteAll(file, sb.toString());
    }

    private boolean isAssembly(String line) {
        if (line.trim().startsWith("//")) {
            return false;
        }
        return line.replaceAll(RECORD_REX, "").trim().isEmpty();
    }

    private Plus<String, String> parseRecord(String line) {
        Plus<String, String> ret = new Plus<>();
        QuotationParser q = new QuotationParser();
        line = q.unQoute(line.trim());
        line = line.replaceFirst(ASSEMBLY_REX, "");
        int st = line.indexOf("(");
        ret.set(line.substring(0, st).trim());
        String value = line.substring(st, line.length());
        value = q.unQoute(value);
        value = q.unQoute(value);
        ret.setExtera(value);
        return ret;
    }

    private String getRecordFor(String key, String value) {
        return "[assembly: " + key + "(\"" + value + "\")]";
    }

}
