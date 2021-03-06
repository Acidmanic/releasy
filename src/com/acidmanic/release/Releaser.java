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

import com.acidmanic.release.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.environment.VersionInspector;
import com.acidmanic.release.releasestrategies.GrantResult;
import com.acidmanic.release.releasestrategies.ReleaseStrategy;
import com.acidmanic.release.versionsources.VersionSourceFile;
import com.acidmanic.release.versioncontrols.VersionControl;
import com.acidmanic.release.versions.VersionModel;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versions.tools.VersionIncrementor;
import com.acidmanic.release.versions.tools.VersionParser;
import com.acidmanic.release.utilities.Result;
import com.acidmanic.release.utilities.trying.Trier;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import com.acidmanic.release.application.AppConfig;
import com.acidmanic.release.versioncontrols.MarkVersionResult;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Releaser {

    private Consumer<VersionModel> afterVersionSelect;

    private Consumer<SetVersionResult> afterVersionSet;

    private final VersionStandard standard;

    private final ReleaseWorkspace workspace;

    private boolean useCredentials = false;
    private String username = null;
    private String password = null;

    public Releaser(ReleaseWorkspace workspace, VersionStandard standard) {

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

    public ReleaseResult release(List<String> changes) {
        // Get Latest version from the source
        VersionModel version = getLatesVersion();
        // Increment the way it should
        VersionIncrementor inc = new VersionIncrementor(this.standard);

        changes.forEach(name -> inc.increment(version, name));

        ReleaseResult result = setVersionToWorkspace(version);

        return result;
    }

    private void commitSourceChangesIntoSourceControl(VersionModel version) {

        File sourcesRoot = this.workspace.getSourceControlRoot();

        if (AppConfig.getSourceControlSystem().isPresent(sourcesRoot)) {

            String commitMessage = getDescription(version);

            AppConfig.getSourceControlSystem()
                    .acceptLocalChanges(sourcesRoot, commitMessage);
        }
    }

    private VersionModel getLatesVersion() {

        VersionInspector inspector = new VersionInspector(workspace.getVersionFilesScanner());

        List<String> allVersionStrings = inspector.getAllPresentedVersionStrings();

        VersionParser parser = new VersionParser(standard);

        VersionModel latest = parser.getZeroVersion();

        for (String versionString : allVersionStrings) {

            Result<VersionModel> result = new Trier().tryFunction(() -> parser.parse(versionString));

            if (result.isSuccess()) {

                VersionModel model = result.getValue();

                if (model.toRawValue() > latest.toRawValue()) {

                    latest = model;
                }
            }
        }
        return latest;
    }

    private SetVersionResult setAllVersions(VersionModel version) {

        VersionInspector inspector = new VersionInspector(this.workspace.getVersionFilesScanner());

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

    private ReleaseResult markReleaseOnVersionControl(VersionModel version) {

        VersionControl versionControl = AppConfig.getVersionControl();

        if (this.useCredentials) {
            versionControl.setCredentials(this.username, this.password);
        } else {
            versionControl.resetCredentials();
        }

        VersionParser parser = new VersionParser(this.standard);

        String message = "Release version " + parser.getVersionString(version);

        String versionString = parser.getTagString(version);

        File sourceRoot = this.workspace.getSourceControlRoot();

        MarkVersionResult markResult = versionControl.markVersion(sourceRoot, versionString, message);

        ReleaseResult result = new ReleaseResult(
                markResult.isSuccessful(),
                markResult.getUpdateSourceControlRemote()
        );
        return result;
    }

    public ReleaseResult setVersionToWorkspace(VersionModel version) {
        // Set new version everywhere
        SetVersionResult results = setAllVersions(version);
        // Check if is it ok to continue the release, regarding the result

        ReleaseStrategy strategy = AppConfig.getReleaseStrategy();

        GrantResult grantResult = strategy.grantContinue(results);

        System.out.println(grantResult.getMessage());

        if (grantResult.isGrant()) {
            // Commit changes on source control
            commitSourceChangesIntoSourceControl(version);
            // Mark release on Version Control
            ReleaseResult result = markReleaseOnVersionControl(version);

            return result;
        }
        return new ReleaseResult();
    }

    // Expose functionality 
    public ReleaseResult setVersionToWorkspace(String versionString) {

        VersionParser parser = new VersionParser(standard);

        Result<VersionModel> result = new Trier().tryFunction(() -> parser.parse(versionString));

        if (result.isSuccess()) {

            VersionModel versionModel = result.getValue();

            return setVersionToWorkspace(versionModel);
        }
        return new ReleaseResult();
    }

    public void setCredentials(String username, String password) {

        this.useCredentials = true;

        this.username = username;

        this.password = password;
    }

    public void resetCredentials() {

        this.useCredentials = false;

        this.username = null;

        this.password = null;
    }
}
