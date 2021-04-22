/*
 * Copyright (C) 2020 Acidmanic
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

import com.acidmanic.release.commands.arguments.IncrementInputAnalyzer;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.release.Releaser;
import com.acidmanic.release.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.commands.arguments.Inc;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.utilities.Emojies;
import java.util.List;

/**
 *
 * @author Acidmanic
 */
public class Auto extends ReleaseCommandBase {

    

    @Override
    protected void addArgumentClasses(TypeRegistery registery) {
        super.addArgumentClasses(registery);

        registery.registerClass(Inc.class);
    }

    

    private void logRelease(boolean success) {
        if (success) {
            log(" " + Emojies.Symbols.CHECK_MARK + "    Successfully released.");
        } else {
            warning("     Release has not been completed.");
        }
        log("");
    }

    @Override
    protected void execute(VersionStandard standard, ReleaseWorkspace workspace, ReleaseContext subCommandsExecutionContext) {

        info("Standard: " + (standard == null ? "NOT FOUND" : standard.getName()));
        info("Workspace:");
        info("\tROOT: " + workspace.getSourceControlRoot().toPath());
        workspace.getVersionFilesScanner().scan(f -> true,
                f -> info("\tPassed File: " + f.toPath().toAbsolutePath()
                        .normalize().toString()));

        Releaser releaser = new Releaser(workspace, standard);

        List<String> changes = new IncrementInputAnalyzer().extractChanges(standard, subCommandsExecutionContext.getIncrementSegmentNames());

        boolean success = releaser.release(changes).isSuccessful();

        logRelease(success);

        if(!success){
            failApplication();
        }
    }

    @Override
    protected String getUsageDescription() {
        return "This command will increment current version from workspace, "
                + "then set it on every version source file. Then incremented "
                + "version would be commited and tagged on available source "
                + "controls in workspace root.";
    }

}
