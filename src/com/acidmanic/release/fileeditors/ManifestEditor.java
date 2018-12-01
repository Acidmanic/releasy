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
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class ManifestEditor {

    private final HashMap<String, String> data;

    public ManifestEditor() {
        data = new HashMap<>();
    }

    public void load(File file) {
        this.data.clear();
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines) {
                String[] parts = line.split("\\:", 2); //Only First
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    set(key, value);
                }
            }
        } catch (Exception e) {
        }
    }

    public String get(String key) {
        if (this.data.containsKey(key)) {
            return this.data.get(key);
        }
        return null;
    }

    public void set(String key, String value) {
        if (this.data.containsKey(key)) {
            this.data.remove(key);
        }
        this.data.put(key, value);
    }

    public void save(File file) {
        try {

            StringBuilder sb = new StringBuilder();
            String sep = "";
            for (String key : data.keySet()) {
                sb.append(sep).append(key).append(": ")
                        .append(data.get(key));
                sep = "\n";
            }
            if (file.exists()) {
                file.delete();
            }
            Files.write(file.toPath(), sb.toString().getBytes(), StandardOpenOption.CREATE);
        } catch (Exception e) {
            System.err.println("");
        }
    }

}
