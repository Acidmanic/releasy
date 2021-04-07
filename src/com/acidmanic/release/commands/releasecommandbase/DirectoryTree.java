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
import java.io.File;

/**
 *
 * @author Acidmanic
 */
public class DirectoryTree extends CommandBase {

    @Override
    protected String getUsageDescription() {
        return "Adds a directory to workspace (Recursively)";
    }

    @Override
    protected String getArgumentsDesciption() {
        return "directory path";
    }

    @Override
    public void execute(String[] args) {

        ReleaseContext context = getContext();

        if (args.length == 1) {
            context.getBundle().addTree(new File(args[0]));
        } else {
            error("Directory path expected.");
        }
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

}
