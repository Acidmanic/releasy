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
package com.acidmanic.release.readmeupdate.updaters;

import com.acidmanic.parse.stringcomparison.StringComparison;
import com.acidmanic.release.fileeditors.AssemblyInfoEditor;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versions.Version;
import com.acidmanic.release.utilities.FileSearch;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
@Deprecated
public class VisualStudio implements Versionable {

    private List<File> assemblyFiles;

    private static final String ASSEMBLY_VERSION = "AssemblyVersion";
    private static final String ASSEMBLY_FILE_VERSION = "AssemblyFileVersion";

    public VisualStudio() {
        this.assemblyFiles = new ArrayList<>();
    }

    @Override
    public void setup(File directory, int releaseType) {
        this.assemblyFiles = findAssemblyFiles();
    }

    @Override
    public boolean isPresent() {
        return !assemblyFiles.isEmpty();
    }

    @Override
    public boolean setVersion(Version version) {

        AssemblyInfoEditor editor = new AssemblyInfoEditor();

        String sVersion = version.getVersionString();

        for (File file : assemblyFiles) {

            try {

                editor.writeValue(file, ASSEMBLY_VERSION, sVersion);

                editor.writeValue(file, ASSEMBLY_FILE_VERSION, sVersion);

            } catch (Exception e) {

                return false;

            }
        }

        return true;
    }

    @Override
    public List<String> getVersions() {

        List<String> versions = new ArrayList<>();

        AssemblyInfoEditor editor = new AssemblyInfoEditor();

        for (File file : assemblyFiles) {

            try {

                addIfNotnull(versions, editor.readValue(file, ASSEMBLY_VERSION));

                addIfNotnull(versions, editor.readValue(file, ASSEMBLY_FILE_VERSION));

            } catch (Exception e) {
            }
        }

        return versions;
    }

    private List<File> findAssemblyFiles() {
        Path here = new File(".").toPath();
        List<File> res = new FileSearch().searchTree(here, "AssemblyInfo.cs", StringComparison.COMPARE_CASE_INSENSITIVE);
        List<File> ret = new ArrayList<>();

        for (File file : res) {
            File parentDirectory = file.getParentFile();
            if (parentDirectory != null
                    && parentDirectory.isDirectory()
                    && parentDirectory.getName().toLowerCase()
                    .compareTo("properties") == 0) {
                ret.add(file);
            }
        }

        return ret;
    }

    private void addIfNotnull(List<String> versions, String version) {

        if (version != null) {
            versions.add(version);
        }
    }

}
