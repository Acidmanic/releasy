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
package com.acidmanic.release.readmeupdate;

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.parse.stringcomparison.StringComparison;
import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.versions.Version;
import com.acidmanic.utilities.ClassRegistery;
import com.acidmanic.utilities.FileSearch;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.acidmanic.release.readmeupdate.updaters.ReadmeUpdater;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class ReadMeVersionSet {

    private List<File> readmes;
    private final String[] nameCandidates = {"readme.md", "readme.txt", "readme"};

    private void checkForReadMe() {
        File here = new File(".");
        readmes = new ArrayList<>();
        FileSearch fs = new FileSearch();
        for (String fileName : nameCandidates) {
            File rmFile = fs.search(here, fileName, StringComparison.COMPARE_CASE_INSENSITIVE);
            if (rmFile != null && rmFile.exists()) {
                readmes.add(rmFile);
            }
        }
    }

    public ReadMeVersionSet() {
        checkForReadMe();
    }

    public void setVersion(Version version, int releaseType) {
        List<ReadmeUpdater> updaters = ClassRegistery.makeInstance().all(ReadmeUpdater.class);
        for (File readme : readmes) {
            setVersion(readme, version, updaters, releaseType);
        }
    }

    private void setVersion(File readme, Version version, List<ReadmeUpdater> updaters, int releaseType) {
        String content = new FileIOHelper().tryReadAllText(readme);
        if (content == null || content.isEmpty()) {
            return;
        }
        Logger.log("INFO: 🆗  Checking " + readme.getName() + " for version mentions.");
        for (ReadmeUpdater updater : updaters) {
            content = updater.process(content, version, releaseType);
        }
        new FileIOHelper().tryWriteAll(readme, content);
    }

}
