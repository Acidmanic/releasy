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
import com.acidmanic.release.commands.directoryscanning.DirectoryScannerBundle;
import static com.acidmanic.release.commands.releasecommandbase.ReleaseParametersExecutionEnvironment.FixedArgument.SCANNERS;
import java.io.File;

/**
 *
 * @author Acidmanic
 */
public class DirectoryTree extends CommandBase implements ReleaseParametersExecutionEnvironment.FixedArgument{
    @Override
    protected String getUsageString() {
        return "";
    }

    @Override
    public void execute() {
        
        DirectoryScannerBundle bundle = getExecutionEnvironment().getDataRepository().get(SCANNERS);
        
        bundle.addTree(new File(args[0]));
    }

    @Override
    public int numberOfArguments() {
        return 1;
    }
    
}
