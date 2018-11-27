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
package com.acidmanic.release.commands;

import acidmanic.commandline.commands.CommandBase;
import com.acidmanic.release.environment.ReleaseEnvironment;
import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.models.ReleaseParameters;
import com.acidmanic.release.versionables.Versionable;
import static com.acidmanic.release.versions.ReleaseTypes.*;
import com.acidmanic.release.versions.Version;
import com.acidmanic.utilities.ReleaseParametersBuilder;
import java.io.File;
import java.util.List;
import release.Application;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public abstract class ReleaseCommandBase extends CommandBase {

    protected ReleaseEnvironment environment = new ReleaseEnvironment();

    protected final static String RELEASE_TYPE_DESCRIPTION
            = "Optional parameter: <release-type> will describe the level "
            + "of development for your release. it can be nightly (default),"
            + "alpha, beta, release-candidate (alternatively: rc) or stable.";

    protected int getReleaseType() {
        String[] names = {"nightly", "alpha", "beta", "release-candidate", "rc", "stable"};
        int[] values = {NIGHTLY, ALPHA, BETA, RELEASE_CANDIDATE, RELEASE_CANDIDATE, STABLE};
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            if (apearedInArgs(name)) {
                return values[i];
            }
        }
        return NIGHTLY;
    }

    private boolean apearedInArgs(String name) {
        for (String arg : args) {
            if (name.compareTo(arg.toLowerCase()) == 0) {
                return true;
            }
        }
        return false;
    }

    protected void performRelease(Version version) {
        List<Versionable> presents = this.environment.getPresentVersionables();
        ReleaseParameters parameters = buildParameters(version, presents);
        printPresentVersionableSystems(parameters);
        if (presetupRelease(parameters)) {
            Application.getReleaseStrategy()
                    .release(parameters);
        }
    }

    private void printPresentVersionableSystems(ReleaseParameters parameters) {
        Logger.log("------- Found Version Systems: -------------------");
        for (Versionable v : parameters.getVersionables()) {
            Logger.log(v.getClass().getSimpleName());
        }
        Logger.log("Release will be performed via: "
                + parameters.getReleaser().getClass().getSimpleName());
        Logger.log("-------------------------------------------------");

    }

    private boolean presetupRelease(ReleaseParameters parameters) {
        if (!parametersValid(parameters)) {
            return false;
        }

        parameters.getVersionables()
                .forEach((Versionable v) -> {
                    v.setup(this.environment.getDirectory(),
                            parameters.getReleaseType());
                });

        parameters.getReleaser().setup(this.environment.getDirectory(),
                parameters.getReleaseType());

        if (!parameters.getReleaser().isPresent()) {
            Logger.log("WARNING: Release tool ("
                    + parameters.getReleaser().getClass().getSimpleName()
                    + ") is not present.");
            Logger.log("WARNING: Nothing has been done.");
            return false;
        }
        return true;
    }

    private boolean parametersValid(ReleaseParameters parameters) {
        if (parameters == null) {
            return false;
        }
        if (parameters.getVersionables().isEmpty()) {
            Logger.log("WARNING: There is no known versionable system.");
            Logger.log("WARNING: Nothing has been done.");
            return false;
        }
        return true;
    }

    protected ReleaseParameters buildParameters(Version version, List<Versionable> presents) {
        return new ReleaseParametersBuilder()
                .releaser(Application.getReleaser())
                .type(getReleaseType())
                .version(version)
                .versionables(presents)
                .build();
    }

}
