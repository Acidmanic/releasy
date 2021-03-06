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

import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.release.Releaser;
import com.acidmanic.release.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.versions.standard.VersionStandard;

/**
 *
 * @author Acidmanic
 */
public class Manual extends ReleaseCommandBase {

    @Override
    protected void execute(VersionStandard standard, ReleaseWorkspace workspace, ReleaseContext subsContext) {

        String version = subsContext.getVersionString();

        Releaser releaser = new Releaser(workspace, standard);

        boolean success = releaser.setVersionToWorkspace(version).isSuccessful();

        if (!success) {
            failApplication();
        }
    }

    @Override
    protected void addArgumentClasses(TypeRegistery registery) {
        super.addArgumentClasses(registery);

        registery.registerClass(com.acidmanic.release.commands.arguments.Version.class);
    }

    @Override
    protected String getUsageDescription() {
        return "This command will set the given version into all version "
                + "source files available in workspace. This version then will "
                + "be tagged via available source controls in workspace root.";
    }

}
