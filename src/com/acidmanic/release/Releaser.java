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
package com.acidmanic.release;

import com.acidmanic.release.environment.ReleaseEnvironment;
import com.acidmanic.release.utilities.VersionProcessor;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versions.Change;
import com.acidmanic.release.versions.Version;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import release.Application;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Releaser {

    /**
     *
     * states
     */
    private List<Versionable> versionables;

    private File directory;
    /**
     *
     * properties
     */
    private Consumer<Version> afterVersionsSet;

  
    public Releaser(File directory) {

        this.directory = directory;

        initialize();
    }

    private void initialize() {

        this.afterVersionsSet = (Version version) -> {
        };

        this.versionables = new ReleaseEnvironment(directory).getPresentVersionables();

    }

    public void release(int releaseType, Change change) {

        Version version = getLatesVersion(releaseType, change);

        release(releaseType, version);

    }

    public void release(int releaseType, Version version) {

        List<Boolean> setResults = setAllVersions(version, releaseType);

        this.afterVersionsSet.accept(version);

        if (Application.getReleaseStrategy().grantContinue(this.versionables, setResults)) {

            if (Application.getSourceControlSystem().isPresent(directory)) {

                Application.getSourceControlSystem()
                        .acceptLocalChanges(directory, getDescription(version));

            }

            performRelease(version, releaseType);
        }

    }

    private Version getLatesVersion(int releaseType, Change change) {

        VersionProcessor processor
                = new VersionProcessor(Application.getVersionFactory());

        List<String> versionStrings = new ReleaseEnvironment(directory).enumAllVersions();

        return processor.generateVersionFromStrings(versionStrings, change, releaseType);

    }

    private List<Boolean> setAllVersions(Version version, int releaseType) {

        List<Boolean> ret = new ArrayList<>();

        for (Versionable versionable : this.versionables) {

            versionable.setup(directory, releaseType);

            ret.add(versionable.setVersion(version));

        }

        return ret;
    }

    private String getDescription(Version version) {
        return "Release version: " + version.getVersionString()
                + ", " + new Date().toString();
    }

    private void performRelease(Version version, int releaseType) {

        Versionable releaser = Application.getReleaser();

        releaser.setup(directory, releaseType);

        releaser.setVersion(version);
    }

    public Consumer<Version> getAfterVersionsSet() {
        return afterVersionsSet;
    }

    public void setAfterVersionsSet(Consumer<Version> afterVersionsSet) {
        this.afterVersionsSet = afterVersionsSet;
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

}
