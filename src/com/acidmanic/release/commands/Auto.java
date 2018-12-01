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

import com.acidmanic.release.environment.ReleaseEnvironment;
import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.models.ReleaseParameters;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versions.Change;
import com.acidmanic.release.versions.Version;
import com.acidmanic.release.utilities.VersionProcessor;
import java.util.List;
import release.Application;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Auto extends ReleaseCommandBase {

    @Override
    protected String getUsageString() {
        return "This Will process previouse versions on all present "
                + "versionable systems and also on relaser. Then makes "
                + "next version for the release based on previuse versions and "
                + "your given parameters, if any."
                + "\n*   "
                + "Optional parameter 'feat' will signals that there was features "
                + "added in submitting code. The 'des' parameter presence shows "
                + " that the code changes include redesigns (major change). and "
                + "the 'fix' parameter represents bug-fixed and patch builds."
                + "\n*   "
                + RELEASE_TYPE_DESCRIPTION;
    }

    @Override
    protected String declareArguments() {
        return "[feat] [des] [fix] [" + RELEASE_TYPE_ARG_DEC + "]";
    }

    @Override
    public void execute() {
        Change change = getChanges();
        int releaseType = getReleaseType();
        List<String> versions = new ReleaseEnvironment().enumAllVersions();
        VersionProcessor processor = new VersionProcessor(Application.getVersionFactory());
        Version version = processor.generateVersionFromStrings(versions, change, releaseType);
        Logger.log("Releasing version: " + version.getVersionString(), this);

        performRelease(version);

    }

    @Override
    protected ReleaseParameters buildParameters(Version version, List<Versionable> presents) {
        ReleaseParameters ret = super.buildParameters(version, presents);
        ret.setChanges(getChanges());
        return ret;
    }

    private Change getChanges() {
        Change ret = new Change(false, false, false);
        for (String arg : args) {
            String a = arg.toLowerCase();
            if ("feat".compareTo(a) == 0) {
                ret.addFeature = true;
            }
            if ("des".compareTo(a) == 0) {
                ret.changeDesign = true;
            }
            if ("fix".compareTo(a) == 0) {
                ret.fixBugs = true;
            }
        }
        return ret;
    }

}
