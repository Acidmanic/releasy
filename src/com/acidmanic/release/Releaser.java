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
import com.acidmanic.release.releasestrategies.GrantResult;
import com.acidmanic.release.releasestrategies.ReleaseStrategy;
import com.acidmanic.release.utilities.VersionProcessor;
import com.acidmanic.release.versionables.VersionSourceFile;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versioncontrols.VersionControl;
import com.acidmanic.release.versions.Change;
import com.acidmanic.release.versions.Version;
import com.acidmanic.release.versions.VersionModel;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versions.standard.VersionStandards;
import com.acidmanic.release.versions.tools.VersionIncrementor;
import com.acidmanic.release.versions.tools.VersionParser;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    private Consumer<Version> afterVersionSelect;

    private Consumer<HashMap<Versionable, Boolean>> afterVersionSet;

    private final VersionStandard standard;

    public Releaser(File directory) {

        this.directory = directory;
        // TODO: make it a consrtuctor dependency
        this.standard = VersionStandards.SIMPLE_SEMANTIC;

        initialize();
    }

    private void initialize() {

        this.afterVersionSelect = (Version version) -> {
        };

        this.afterVersionSet = (HashMap<Versionable, Boolean> t) -> {
        };

        this.versionables = new ReleaseEnvironment(directory).getPresentVersionables();

    }

    @Deprecated
    public boolean release(int releaseType, Change change) {

        Version version = getLatesVersion(releaseType, change);

        return release(releaseType, version);

    }

    @Deprecated
    public boolean release(int releaseType, Version version) {

        this.afterVersionSelect.accept(version);

        List<Boolean> setResults = setAllVersions(version, releaseType);

        this.afterVersionSet.accept(hashResult(this.versionables, setResults));

        if (Application.getReleaseStrategy().grantContinue(this.versionables, setResults)) {

            if (Application.getSourceControlSystem().isPresent(directory)) {

                Application.getSourceControlSystem()
                        .acceptLocalChanges(directory, getDescription(version));

            }

            return markReleaseOnVersionControl(version, releaseType);
        }

        return false;
    }

    private Version getLatesVersion(int releaseType, Change change) {

        VersionProcessor processor
                = new VersionProcessor(Application.getVersionFactory());

        List<String> versionStrings = new ReleaseEnvironment(directory).enumAllVersions();

        return processor.generateVersionFromStrings(versionStrings, change, releaseType);

    }

    @Deprecated
    private List<Boolean> setAllVersions(Version version, int releaseType) {

        List<Boolean> ret = new ArrayList<>();

        for (Versionable versionable : this.versionables) {

            versionable.setup(directory, releaseType);

            ret.add(versionable.setVersion(version));

        }

        return ret;
    }

    @Deprecated
    private String getDescription(Version version) {
        return "Release version: " + version.getVersionString()
                + ", " + new Date().toString();
    }

    @Deprecated
    private boolean markReleaseOnVersionControl(Version version, int releaseType) {

        Versionable releaser = Application.getReleaser();

        releaser.setup(directory, releaseType);

        return releaser.setVersion(version);
    }

    public void setAfterVersionSet(Consumer<HashMap<Versionable, Boolean>> afterVersionSet) {
        this.afterVersionSet = afterVersionSet;
    }

    public void setAfterVersionSelect(Consumer<Version> afterVersionSelect) {
        this.afterVersionSelect = afterVersionSelect;
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    @Deprecated
    private HashMap<Versionable, Boolean> hashResult(List<Versionable> versionables, List<Boolean> setResults) {

        HashMap<Versionable, Boolean> ret = new HashMap<>();

        for (int i = 0; i < versionables.size(); i++) {
            ret.put(versionables.get(i), setResults.get(i));
        }

        return ret;
    }

    public boolean release(VersionStandard standard, ArrayList<String> changes) {
        // Get Latest version from the source
        VersionModel version = getLatesVersion();
        // Increment the way it should
        VersionIncrementor inc = new VersionIncrementor(standard);

        changes.forEach(name -> inc.increment(version, name));
        // Set new version everywhere
        SetVersionResult results = setAllVersions(version);
        // Check if is it ok to continue the release, regarding the result
        
        ReleaseStrategy strategy = Application.getReleaseStrategy();
        
        GrantResult grantResult = strategy.grantContinue(results);
        
        System.out.println(grantResult.getMessage());
        
        if (grantResult.isGrant()) {
            // Commit changes on source control
            commitSourceChangesIntoSourceControl(version);
            // Mark release on Version Control
            markReleaseOnVersionControl(version);
        }
        return false;
    }

    private void commitSourceChangesIntoSourceControl(VersionModel version) {

        if (Application.getSourceControlSystem().isPresent(directory)) {

            String commitMessage = getDescription(version);

            Application.getSourceControlSystem()
                    .acceptLocalChanges(directory, commitMessage);
        }
    }

    private VersionModel getLatesVersion() {

        List<String> allVersionStrings = new ReleaseEnvironment(this.directory)
                .getAllPresentedVersionStrings();

        VersionModel latest = zeroVersion();

        for (String versionString : allVersionStrings) {

            VersionModel model = tryParse(versionString);

            if (model.toRawValue() > latest.toRawValue()) {

                latest = model;
            }
        }
        return latest;
    }

    private SetVersionResult setAllVersions(VersionModel version) {

        List<VersionSourceFile> sourceFiles = new ReleaseEnvironment(this.directory)
                .getPresentVersionSourceFiles();

        SetVersionResult ret = new SetVersionResult();

        VersionParser parser = new VersionParser(this.standard);

        String versionString = parser.getVersionString(version);

        for (VersionSourceFile source : sourceFiles) {

            boolean res = source.setVersion(versionString);

            ret.add(source, res);
        }
        return ret;
    }

    private String getDescription(VersionModel version) {

        VersionParser parser = new VersionParser(this.standard);

        return "Release version: " + parser.getVersionString(version)
                + ", " + new Date().toString();
    }

    private void markReleaseOnVersionControl(VersionModel version) {

        VersionControl versionControl = Application.getVersionControl();

        VersionParser parser = new VersionParser(this.standard);

        String message = "Release version " + parser.getVersionString(version);

        String versionString = parser.getTagString(version);

        versionControl.markVersion(this.directory, versionString, message);
    }

    private VersionModel zeroVersion() {

        int length = this.standard.getSections().size();

        VersionModel ret = new VersionModel(length);

        for (int i = 0; i < length; i++) {

            ret.setValue(i, 0);

            long order = this.standard.getSections().get(i).getGlobalWeightOrder();

            ret.setOrder(i, order);
        }
        return ret;
    }

    private VersionModel tryParse(String versionStringn) {

        VersionParser parser = new VersionParser(this.standard);

        try {

            return parser.parse(versionStringn);

        } catch (Exception e) {
            return zeroVersion();
        }
    }

}
