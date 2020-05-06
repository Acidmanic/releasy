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

import com.acidmanic.release.commands.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.environment.Inspector;
import com.acidmanic.release.releasestrategies.GrantResult;
import com.acidmanic.release.releasestrategies.ReleaseStrategy;
import com.acidmanic.release.versionables.versionsources.VersionSourceFile;
import com.acidmanic.release.versioncontrols.VersionControl;
import com.acidmanic.release.versions.VersionModel;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versions.tools.VersionIncrementor;
import com.acidmanic.release.versions.tools.VersionParser;
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
public class Releaser2 {

    private Consumer<VersionModel> afterVersionSelect;

    private Consumer<SetVersionResult> afterVersionSet;

    private final VersionStandard standard;

    private final ReleaseWorkspace workspace;

    public Releaser2(ReleaseWorkspace workspace, VersionStandard standard) {

        this.workspace = workspace;

        this.standard = standard;

        initialize();
    }

    private void initialize() {

        this.afterVersionSelect = (VersionModel version) -> {
        };

        this.afterVersionSet = (SetVersionResult r) -> {
        };

    }

    public void setAfterVersionSelect(Consumer<VersionModel> afterVersionSelect) {
        this.afterVersionSelect = afterVersionSelect;
    }

    public void setAfterVersionSet(Consumer<SetVersionResult> afterVersionSet) {
        this.afterVersionSet = afterVersionSet;
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

        File sourcesRoot = this.workspace.getSourceControlRoot();

        if (Application.getSourceControlSystem().isPresent(sourcesRoot)) {

            String commitMessage = getDescription(version);

            Application.getSourceControlSystem()
                    .acceptLocalChanges(sourcesRoot, commitMessage);
        }
    }

    private VersionModel getLatesVersion() {

        Inspector inspector = new Inspector(workspace.getVersionFilesScanner());

        List<String> allVersionStrings = inspector.getAllPresentedVersionStrings();

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

        Inspector inspector = new Inspector(this.workspace.getVersionFilesScanner());

        List<VersionSourceFile> sourceFiles = inspector.getPresentVersionSourceFiles();

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

        File sourceRoot = this.workspace.getSourceControlRoot();

        versionControl.markVersion(sourceRoot, versionString, message);
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
