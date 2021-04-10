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

import com.acidmanic.release.commands.arguments.SourceRoot;
import com.acidmanic.release.commands.arguments.Directory;
import com.acidmanic.release.commands.arguments.DirectoryRadical;
import com.acidmanic.release.commands.arguments.DirectoryTree;
import com.acidmanic.commandline.commands.FractalCommandBase;
import com.acidmanic.commandline.commands.Help;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.consoletools.terminal.Terminal;
import com.acidmanic.consoletools.terminal.styling.TerminalControlEscapeSequences;
import com.acidmanic.consoletools.terminal.styling.TerminalStyle;
import com.acidmanic.release.commands.ReleaseContext;
import com.acidmanic.release.commands.directoryscanning.DirectoryScannerBundle;
import com.acidmanic.release.commands.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versions.standard.VersionStandards;
import com.acidmanic.release.versionstandard.StandardProvider;
import java.io.File;

/**
 *
 * @author Acidmanic
 */
public abstract class ReleaseCommandBase extends FractalCommandBase<ReleaseContext> {

    @Override
    protected void addArgumentClasses(TypeRegistery registery) {
        registery.registerClass(Directory.class);
        registery.registerClass(DirectoryTree.class);
        registery.registerClass(DirectoryRadical.class);
        registery.registerClass(com.acidmanic.release.commands.arguments.VersionStandard.class);
        registery.registerClass(SourceRoot.class);
        registery.registerClass(Help.class);
    }

    @Override
    protected ReleaseContext createNewContext() {
        ReleaseContext context = new ReleaseContext();

        context.setBundle(new DirectoryScannerBundle());

        context.setRoot(new File("."));

        context.setStandardName(VersionStandards.SIMPLE_SEMANTIC.getName());

        return context;
    }
    
    private void postProcessContext(ReleaseContext context){
        if(context.getBundle()==null){
            context.setBundle(new DirectoryScannerBundle());
        }
        if(context.getBundle().isEmpty()){
            
            File here = new File(".").toPath().toAbsolutePath().normalize().toFile();
            
            context.getBundle().addCurrentDirectory(here);
        }
        File root = context.getRoot();
        
        if(root==null || !root.exists() || !root.isDirectory()){
            
            root = new File(".").toPath().toAbsolutePath().normalize().toFile();
        }
        context.setRoot(root);
    }

    @Override
    protected void execute(ReleaseContext subCommandsExecutionContext) {

        postProcessContext(subCommandsExecutionContext);
        
        File root = subCommandsExecutionContext.getRoot().toPath()
                .toAbsolutePath().normalize().toFile();

        ReleaseWorkspace workspace = new ReleaseWorkspace(
                subCommandsExecutionContext.getBundle(),
                root);

        String standardName = subCommandsExecutionContext.getStandardName();

        VersionStandard standard = new StandardProvider().getStandard(standardName);

        info("---------\t" + this.getClass().getSimpleName() + "\t---------");

        execute(standard, workspace, subCommandsExecutionContext);
    }

    protected abstract void execute(VersionStandard standard,
            ReleaseWorkspace workspace,
            ReleaseContext subCommandsExecutionContext);

    @Override
    protected void info(String text) {
        TerminalStyle style = new TerminalStyle(
                TerminalControlEscapeSequences.FOREGROUND_CYAN
        );
        Terminal terminal = new Terminal();

        terminal.setScreenAttributes(style);

        System.out.println(text);

        terminal.resetScreenAttributes();
    }
}
