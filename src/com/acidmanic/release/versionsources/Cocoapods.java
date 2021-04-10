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
package com.acidmanic.release.versionsources;

import com.acidmanic.release.directoryscanning.DirectoryScannerBundle;
import com.acidmanic.release.fileeditors.SpecFileEditor;
import com.acidmanic.release.projectdirectory.XCodeProjectDirectoryInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Cocoapods implements VersionSourceFile {

    private final static String PODSPEC_EXT = ".podspec";

    private class Pod {

        public File directory;
        public String projectName;
        public File specsFile;
    }

    private final List<Pod> pods = new ArrayList<>();

    private static File getSpecFile(File directory, String projectName) {
        return directory.toPath().resolve(projectName + PODSPEC_EXT)
                .toFile();
    }

    @Override
    public boolean isPresent() {
        return !this.pods.isEmpty();
    }

    @Override
    public List<String> getVersions() {

        ArrayList<String> versions = new ArrayList<>();

        for (Pod pod : pods) {

            List<String> vers = getVersions(pod);

            versions.addAll(vers);
        }
        return versions;
    }

    private List<String> getVersions(Pod pod) {
        ArrayList<String> ret = new ArrayList<>();
        if (isPresent()) {
            try {
                ret.add(
                        new SpecFileEditor(pod.specsFile)
                                .getVerion());
                return ret;
            } catch (Exception e) {
            }
        }
        return ret;
    }

    @Override
    public void setup(DirectoryScannerBundle scanners) {

        List<File> allDirectories = new ArrayList();

        scanners.scan(f -> f.isDirectory(), f -> allDirectories.add(f));

        pods.clear();

        for (File directory : allDirectories) {

            String projectName = new XCodeProjectDirectoryInfo().getProjectName(directory);

            if (projectName != null) {

                File specsFile = getSpecFile(directory, projectName);

                if (specsFile.exists()) {
                    Pod pod = new Pod();

                    pod.directory = directory;
                    pod.projectName = projectName;
                    pod.specsFile = specsFile;

                    pods.add(pod);
                }
            }
        }
    }

    @Override
    public boolean setVersion(String versionString) {
        boolean result = !pods.isEmpty();

        for (Pod pod : pods) {
            try {
                new SpecFileEditor(pod.specsFile)
                        .setVerion(versionString);
            } catch (Exception e) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Cocoapods";
    }

}
