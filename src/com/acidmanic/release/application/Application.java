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
package com.acidmanic.release.application;

import com.acidmanic.commandline.commands.Command;
import com.acidmanic.commandline.commands.CommandFactory;
import com.acidmanic.lightweight.logger.ConsoleLogger;
import java.util.HashMap;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        AppConfig.initialize();

        ApplicationContext context = new ApplicationContext();

        CommandFactory factory = new CommandFactory(
                AppConfig.getCommandsRegistery(),
                 new ConsoleLogger(), context
        );

        HashMap<Command, String[]> commands = factory.make(args, true);

        commands.forEach((c, a) -> c.execute(a));
        
        
        if(!context.getExecutionSuccess()){
            System.err.println("------------------------------");
            System.err.println(context.getFailureMessage());
            System.err.println("------------------------------");
            
            System.exit(-1);
        }

    }

}
