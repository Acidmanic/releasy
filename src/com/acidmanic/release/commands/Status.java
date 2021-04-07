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

import com.acidmanic.release.commands.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.commands.releasecommandbase.ReleaseCommandBase2;
import com.acidmanic.release.environment.Inspector;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versionsources.VersionSourceFile;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Status extends ReleaseCommandBase2 {

    @Override
    protected void execute(VersionStandard standard, ReleaseWorkspace workspace, ReleaseContext subCommandsExecutionContext) {

        Inspector inspector = new Inspector(workspace.getVersionFilesScanner());

        HashMap<VersionSourceFile, List<String>> presentVersions = inspector.getAllPresentVersionStringsAndFiles();

        for (VersionSourceFile source : presentVersions.keySet()) {

            info("\n" + source.getName() + ": ");

            info("----------------------");

            List<String> versionsStrings = presentVersions.get(source);

            versionsStrings.forEach(ver -> info("\t" + ver));

        }

    }

    @Override
    protected String getUsageDescription() {
        return "This command will list and view any version source and any "
                + "released versions on any available version controls present"
                + " at the workspace";
    }

}
