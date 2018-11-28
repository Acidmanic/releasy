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
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versions.Version;
import com.acidmanic.utilities.VersionProcessor;
import java.util.List;
import release.Application;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Status extends CommandBase {

    private final static String LINE = "-----------------------------------------";

    @Override
    protected String getUsageString() {
        return "Will shows latest Version provided previously (if any). "
                + "And also will list any available Versionable system.";
    }

    @Override
    public void execute() {
        ReleaseEnvironment environment = new ReleaseEnvironment();
        List<String> sVersions = environment.enumAllVersions();
        List<Versionable> versionables = environment.getPresentVersionables();
        VersionProcessor processor = new VersionProcessor(Application.getVersionFactory());

        List<Version> versions = processor.toVersionList(sVersions);
        Version latestOfAll = processor.getLatest(versions);

        log(LINE);

        if (sVersions.isEmpty() || versions.isEmpty() || latestOfAll == null
                || latestOfAll == Version.NULL) {
            log("no version has been introduced by now.");
        } else {
            log("Latest version introduced in workspace is: " + latestOfAll.getVersionString());
        }

        log(LINE);

        if (versionables.isEmpty()) {
            log("No Versionable systems are detected in current workspace.");
        } else {
            log("Detected versionables: ");
            for (Versionable v : versionables) {
                String line = v.getClass().getSimpleName();
                List<String> svers = v.getVersions();
                if (!svers.isEmpty()) {
                    List<Version> vers = processor.toVersionList(svers);
                    if (!vers.isEmpty()) {
                        line += " -> " + processor.getLatest(vers).getVersionString();
                    }
                }
                log(line);
            }
        }

        log(LINE);

    }

}
