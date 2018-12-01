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
                    if (this.data.containsKey(parts[0])) {
                        this.data.remove(parts[0]);
                    }
                    this.data.put(parts[0], parts[1]);
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

}
