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
import com.acidmanic.release.utilities.VersionProcessor;
import java.io.File;
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
            log("");
            for (Versionable v : versionables) {
                printVersionableState(v, processor);
            }
        }

        log(LINE);

        if (Application.getReleaser() == null) {
            log("There are no releasers configured in your settings.");
        } else {
            Application.getReleaser().setup(new File("."), 0);
            String releaserName = Application.getReleaser().getClass().getSimpleName();
            if (Application.getReleaser().isPresent()) {
                log("Releaser:");
                printVersionableState(Application.getReleaser(), processor);
            } else {
                log("Your Releaser (" + releaserName + ") is not present.");
            }
        }
        log(LINE);
    }

    private void printVersionableState(Versionable v, VersionProcessor processor) {
        String line = v.getClass().getSimpleName();
        String latest = getLatestVersion(v, processor);
        if (latest != null) {
            line += " -> " + latest;
        }
        System.out.println(line);
    }

    private String getLatestVersion(Versionable versionable, VersionProcessor processor) {
        versionable.setup(new File("."), 0);
        List<String> svers = versionable.getVersions();
        if (!svers.isEmpty()) {
            List<Version> vers = processor.toVersionList(svers);
            if (!vers.isEmpty()) {
                Version latest = processor.getLatest(vers);
                if (latest != null && latest != Version.NULL) {
                    return latest.getVersionString();
                }
            }
        }
        return null;
    }
}
