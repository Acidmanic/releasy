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
package com.acidmanic.utilities;

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
 * tag to use it as text by adding \ prefix. In other words,
 * \LATEST_RELEASE_VERSION_TAG will be replaced just with
 * LATEST_RELEASE_VERSION_TAG.
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class TextFileUpdater {

    private final String charset;
    private final char escape;
    private final String tag;

    public TextFileUpdater(String tag) {
        this.tag = tag;
        this.escape = '\\';
        this.charset = "UTF-8";
    }

    public TextFileUpdater(String tag, char escape) {
        this.charset = "UTF-8";
        this.escape = escape;
        this.tag = tag;
    }

    public TextFileUpdater(String tag, char escape, String charset) {
        this.charset = charset;
        this.escape = escape;
        this.tag = tag;
    }

    public void updateFiles(List<String> files, String version) {
        Path here = new File(".").toPath();
        files.forEach((String path) -> updateFile(here.resolve(path).toFile(), version));
    }

    public void updateFile(File file, String version) {
        if (file.exists()) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()), charset);
                content = new Replacement().replace(content, tag, version, escape);
                file.delete();
                Files.write(file.toPath(), content.getBytes(charset), StandardOpenOption.CREATE);
            } catch (Exception e) {
            }
        }
    }

}
