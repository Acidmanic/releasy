/*
 * Copyright (C) 2020 Acidmanic
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
package com.acidmanic.release.versionstandard;

import com.acidmanic.release.fileeditors.JsonEditor;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versions.standard.VersionStandards;
import com.acidmanic.release.utilities.ApplicationInfo;
import com.acidmanic.release.utilities.DirectoryHelper;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Acidmanic
 */
public class StandardProvider {

    private HashMap<String, VersionStandard> standards;

    public StandardProvider(){
        this(DirectoryHelper.SCANE_MODE_CURRENT_DIRECTORY);
    }
    
    public StandardProvider(int scanMode) {

        this.standards = new HashMap<>();

        List<VersionStandard> result = listBuiltIns();

        result.forEach(s -> standards.put(s.getName().toLowerCase(), s));

        result = listJsons(scanMode);

        result.forEach(s -> standards.put(s.getName().toLowerCase(), s));
    }

    public VersionStandard getStandard(String name) {

        name = name.toLowerCase();

        if (this.standards.containsKey(name)) {

            return this.standards.get(name);
        }
        return null;
    }

    private List<VersionStandard> listBuiltIns() {

        List<VersionStandard> ret = new ArrayList<>();

        Class<VersionStandards> type = VersionStandards.class;

        Field[] fields = type.getFields();

        for (Field field : fields) {

            if (field.getType().equals(VersionStandard.class)) {

                try {
                    VersionStandard standard = (VersionStandard) field.get(null);

                    ret.add(standard);

                } catch (Exception e) {
                }
            }
        }
        return ret;
    }

    private List<VersionStandard> listJsons(int scanMode) {
        
        List<VersionStandard> ret = new ArrayList<>();

        File dir = new ApplicationInfo().getExecutionDirectory();

        new DirectoryHelper().scanFiles(dir,
                 f -> f.getName().toLowerCase().endsWith(".json"),
                 f -> addValidStandard(ret, f),scanMode);
        
        return ret;
    }

    public VersionStandard fromFile(File file) {
        
        try {
            JsonEditor editor = new JsonEditor(file);

            VersionStandard standard = editor.load(VersionStandard.class);

            if (isValidStandard(standard)) {

                return standard;
            }
        } catch (Exception e) {        }

        return null;
    }

    private boolean isValidStandard(VersionStandard standard) {
        
        if (standard != null) {
            
            if (standard.getName() != null && standard.getName().length() > 0) {
                
                if (!standard.getSections().isEmpty()) {
                    
                    return true;
                }
            }
        }
        return false;
    }

    private void addValidStandard(List<VersionStandard> out, File file) {

        VersionStandard standard = fromFile(file);

        if (standard != null) {

            out.add(standard);
        }
    }
    
    public List<String> availableStandards(){
        
        List<String> ret = new ArrayList<>();
        
        this.standards.values().forEach( s -> ret.add(s.getName()));
        
        return ret;
    }
}
