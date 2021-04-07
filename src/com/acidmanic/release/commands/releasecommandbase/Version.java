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
package com.acidmanic.release.commands.releasecommandbase;

import com.acidmanic.commandline.commands.CommandBase;
import com.acidmanic.release.commands.ReleaseContext;

/**
 *
 * @author Acidmanic
 */
public class Version extends CommandBase {

    @Override
    protected String getUsageDescription() {
        return "Sets the version to given version.";
    }

    @Override
    protected String getArgumentsDesciption() {
        return "Version string";
    }

    @Override
    public void execute(String[] args) {

        if (args.length == 1) {
            ReleaseContext context = getContext();

            context.setVersionString(args[0]);
        } else {
            error("Version string expected.");
        }
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

}
