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

import com.acidmanic.commandline.application.ExecutionDataRepository;
import com.acidmanic.commandline.commands.CommandBase;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.consoletools.terminal.Terminal;
import com.acidmanic.consoletools.terminal.styling.TerminalControlEscapeSequences;
import com.acidmanic.consoletools.terminal.styling.TerminalStyle;
import com.acidmanic.release.commands.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versionstandard.StandardProvider;
import java.io.File;

/**
 *
 * @author Acidmanic
 */
public abstract class ReleaseCommandBase2 extends CommandBase {

    @Override
    public void execute() {

        TypeRegistery registery = new ReleaseParametersTypeRegistery();

        ReleaseParametersExecutionEnvironment env
                = new ReleaseParametersExecutionEnvironment(registery);

        env.execute(args);

        File root = env.getRootDirectory();

        ReleaseWorkspace workspace = new ReleaseWorkspace(env.getScanners(), root);

        String standardName = env.getStandardName();

        VersionStandard standard = new StandardProvider().getStandard(standardName);

        execute(standard, workspace, env.getDataRepository());

    }
    
    @Override
    protected void info(String text){
        TerminalStyle style = new TerminalStyle(
                TerminalControlEscapeSequences.FOREGROUND_CYAN
        );
        Terminal terminal = new Terminal();
        
        terminal.setScreenAttributes(style);
        
        System.out.println(text);
        
        terminal.resetScreenAttributes();
    }

    protected abstract void execute(VersionStandard standard,
             ReleaseWorkspace workspace,
             ExecutionDataRepository dataRepository);
}
