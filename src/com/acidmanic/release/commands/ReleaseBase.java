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
import com.acidmanic.release.Releaser;
import com.acidmanic.release.environment.ReleaseEnvironment;
import com.acidmanic.release.readmeupdate.ReadMeVersionSet;
import com.acidmanic.release.versions.Change;

import static com.acidmanic.release.versions.ReleaseTypes.ALPHA;
import static com.acidmanic.release.versions.ReleaseTypes.BETA;
import static com.acidmanic.release.versions.ReleaseTypes.NIGHTLY;
import static com.acidmanic.release.versions.ReleaseTypes.RELEASE_CANDIDATE;
import static com.acidmanic.release.versions.ReleaseTypes.STABLE;
import java.io.File;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public abstract class ReleaseBase extends CommandBase {

    protected final static String RELEASE_TYPE_ARG_DEC = "<release-type>";
    protected final static String RELEASE_TYPE_DESCRIPTION
            = "<release-type> will describe the level "
            + "of development for your release. it can be nightly (default),"
            + "alpha, beta, release-candidate (alternatively: rc) or stable.";

    @Override
    public void execute() {

    }

    private int releaseType;

    public void setArgs(String[] args) {
        this.args = args;
        this.releaseType = getReleaseType();
    }

    private Releaser makeReleaser() {
        Releaser r = new Releaser(new ReleaseEnvironment().getDirectory());

        r.setAfterVersionsSet((com.acidmanic.release.versions.Version v)
                -> {
            log("INFO: Setting version to: " + v.getVersionString());
            new ReadMeVersionSet().setVersion(v, releaseType);
        });

        return r;
    }

    /**
     * Auto create version
     */
    protected void release(Change change) {
        makeReleaser().release(releaseType, change);
    }

    protected void release(com.acidmanic.release.versions.Version version) {
        makeReleaser().release(releaseType, version);
    }

    private int getReleaseType() {
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

}
