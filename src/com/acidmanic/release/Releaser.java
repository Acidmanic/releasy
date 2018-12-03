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
import com.acidmanic.release.releasestrategies.ReleaseStrategy;
import com.acidmanic.release.utilities.VersionProcessor;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versions.ReleaseTypes;
import com.acidmanic.release.versions.Version;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    /**
     *
     * properties
     */
    private Runnable afterVersionsSet;

    private ReleaseStrategy releaseStrategy;

    public Releaser() {

        initialize();

    }

    private void initialize() {

        this.afterVersionsSet = () -> {
        };

        this.versionables = new ReleaseEnvironment().getPresentVersionables();
    }

    public void release() {
        this.release(ReleaseTypes.NIGHTLY);
    }

    public void release(int releaseType) {

        Version version = getLatesVersion();

        List<Boolean> setResults = setAllVersions(version, releaseType);

        this.afterVersionsSet.run();

        if (this.releaseStrategy.grantContinue(this.versionables, setResults)) {

            if (Application.getSourceControlSystem().isPresent()) {

                Application.getSourceControlSystem()
                        .acceptLocalChanges(getDescription(version));

            }

            performRelease(version, releaseType);
        }

    }

    private Version getLatesVersion() {

        VersionProcessor processor
                = new VersionProcessor(Application.getVersionFactory());

        List<String> versionStrings = new ReleaseEnvironment().enumAllVersions();

        List<Version> versions = processor.toVersionList(versionStrings);

        return processor.getLatest(versions);

    }

    private List<Boolean> setAllVersions(Version version, int releaseType) {

        List<Boolean> ret = new ArrayList<>();

        File here = new ReleaseEnvironment().getDirectory();

        for (Versionable versionable : this.versionables) {

            versionable.setup(here, releaseType);

            ret.add(versionable.setVersion(version));

        }

        return ret;
    }

    private String getDescription(Version version) {
        return "Release to version: " + version.getVersionString()
                + ", " + new Date().toString();
    }

    private void performRelease(Version version, int releaseType) {

        Versionable releaser = Application.getReleaser();

        releaser.setup(new ReleaseEnvironment().getDirectory(), releaseType);

        releaser.setVersion(version);
    }

    public Runnable getAfterVersionsSet() {
        return afterVersionsSet;
    }

    public void setAfterVersionsSet(Runnable afterVersionsSet) {
        this.afterVersionsSet = afterVersionsSet;
    }

    public ReleaseStrategy getReleaseStrategy() {
        return releaseStrategy;
    }

    public void setReleaseStrategy(ReleaseStrategy releaseStrategy) {
        this.releaseStrategy = releaseStrategy;
    }

}
