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
package com.acidmanic.release.configurabletools;

import com.acidmanic.parse.Replacement;
import com.acidmanic.release.versions.Version;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 *
 * This tool will update all listed text-files with given version. this will
 * simply replace the LATEST_RELEASE_VERSION_TAG in any file. you can escape the
 * tag to use it as text by adding \ prefix. In other words, \LATEST_RELEASE_VERSION_TAG 
 * will be replaced just with LATEST_RELEASE_VERSION_TAG. 
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class TextFileUpdater {

    public static final String LATEST_RELEASE_VERSION_TAG = "LATEST_RELEASE_VERSION_TAG";
    public static final int TAG_LENGTH = LATEST_RELEASE_VERSION_TAG.length();
    public static final char ESCAPE = '\\';
    private final String CHARSET="UTF-8";
    
    
    
    public void updateFiles(List<String> files,Version version){
        Path here = new File(".").toPath();
        String v = version.getVersionString();
        files.forEach((String path) -> updateFile(here.resolve(path).toFile(),v));
    }
    
    public void updateFile(File file,String version){
        if(file.exists()){
            try {
                String content = new String(Files.readAllBytes(file.toPath()),CHARSET);
                content = new Replacement().replace(content, LATEST_RELEASE_VERSION_TAG, version, ESCAPE);
                file.delete();
                Files.write(file.toPath(), content.getBytes(CHARSET), StandardOpenOption.CREATE);
            } catch (Exception e) {
            }
        }
    }

    
}
