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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class JsonEditor {

    private File file;

    public JsonEditor(File file) {
        this.file = file;
    }

    public String readValue(String[] address) {
        try {
            Map<String, Object> mapped = readAsObjectMap(file);

            for (int i = 0; i < address.length - 1; i++) {
                String name = address[i];
                mapped = (Map<String, Object>) mapped.get(name);
            }
            return (String) mapped.get(address[address.length - 1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, Object> readAsObjectMap(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mapped = mapper.readValue(file, new TypeReference<Map<String, Object>>() {
        });
        return mapped;
    }

    public void setValue(String[] address, String value) {
        try {
            Map<String, Object> main = readAsObjectMap(file);
            Map<String, Object> mapped = main;
            for (int i = 0; i < address.length - 1; i++) {
                String name = address[i];
                mapped = (Map<String, Object>) mapped.get(name);
            }
            String lastKey = address[address.length - 1];
            if (mapped.containsKey(lastKey)) {
                mapped.remove(lastKey);
            }
            mapped.put(lastKey, value);
            writeObjectMap(main, file);
        } catch (Exception e) {
        }
    }

    private void writeObjectMap(Map<String, Object> objectMap, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, objectMap);
    }
}
