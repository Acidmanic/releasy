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

import com.acidmanic.commandline.application.ExecutionEnvironment;
import com.acidmanic.commandline.commands.Command;
import com.acidmanic.commandline.commands.CommandBase;
import com.acidmanic.commandline.commands.CommandFactory;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.release.commands.directoryscanning.DirectoryScannerBundle;
import com.acidmanic.release.versions.standard.VersionStandards;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acidmanic
 */
public class ReleaseParametersExecutionEnvironment extends ExecutionEnvironment {

    public interface FixedArgument {

        public static final String VERSION_STANDARD = "VS";
        public static final String SCANNERS = "SCN";
        public static final String ROOT = "ROOT";

        int numberOfArguments();
    }

    private class CommandsExtractionResult {

        public boolean hasHelp;
        public List<Command> commands = new ArrayList<>();
        public CommandBase help = null;
    }

    private CommandsExtractionResult extractCommands(String[] args) {

        int argsIndex = 0;

        CommandFactory factory = new CommandFactory(this.getTypeRegistery());

        CommandsExtractionResult commands = new CommandsExtractionResult();

        while (argsIndex < args.length) {

            String[] arguments = subArray(args, argsIndex);

            Command command = factory.makeCommand(arguments);

            command.setExecutionEnvironment(this);

            command.setCreatorFactory(factory);

            if (command instanceof FixedArgument) {

                argsIndex += ((FixedArgument) command).numberOfArguments() + 1;
                //Assuming all help commands extend librariys Help command
                if (command instanceof com.acidmanic.commandline.commands.Help) {

                    commands.help = (CommandBase) command;

                    commands.hasHelp = true;
                } else {
                    commands.commands.add(command);
                }

            } else {
                argsIndex += 1;
            }
        }
        return commands;
    }

    public ReleaseParametersExecutionEnvironment(TypeRegistery registery) {
        super(registery);

        this.getDataRepository().set(FixedArgument.SCANNERS, new DirectoryScannerBundle());

        this.getDataRepository().set(FixedArgument.VERSION_STANDARD,
                VersionStandards.SIMPLE_SEMANTIC.getName());

        this.getDataRepository().set(FixedArgument.ROOT, new File("."));
    }

    public void executeAll(String[] args) {

        CommandsExtractionResult commands = extractCommands(args);

        if (commands.hasHelp) {
            
            commands.help.execute();
            
        } else {
            commands.commands.forEach(cmd -> cmd.execute());
        }

    }

    private String[] subArray(String[] array, int index) {

        if (index >= array.length) {

            return new String[]{};
        }
        int length = array.length - index;

        String[] args = new String[length];

        for (int i = 0; i < length; i++) {
            args[i] = array[i + index];
        }
        return args;
    }

    public DirectoryScannerBundle getScanners() {

        DirectoryScannerBundle ret = getDataRepository().get(FixedArgument.SCANNERS);

        if (ret == null) {

            ret = new DirectoryScannerBundle();
        }
        if (ret.isEmpty()) {

            File here = new File(".");

            ret.addCurrentDirectory(here);
        }

        return ret;
    }

    public String getStandardName() {

        String ret = getDataRepository().get(FixedArgument.VERSION_STANDARD);

        return ret;
    }

    public File getRootDirectory() {

        File ret = getDataRepository().get(FixedArgument.ROOT);

        if (ret == null || !ret.exists() || !ret.isDirectory()) {
            ret = new File(".");
        }

        return ret;
    }

}
