/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.commands.arguments;

import com.acidmanic.commandline.commands.CommandBase;
import com.acidmanic.release.commands.ReleaseContext;

/**
 *
 * @author diego
 */
public class Auth extends CommandBase {

    @Override
    protected String getUsageDescription() {
        return "this argument sets username and password for authentications if necessary."
                + "username and password would be passed separated by comma: "
                + "\"<user-name>,<password>\". ";
    }

    @Override
    protected String getArgumentsDesciption() {
        return "";
    }

    @Override
    public void execute(String[] args) {

        ReleaseContext context = getContext();

        if (args.length > 0) {
            context.setUsername(args[0]);

            if (args.length == 2) {
                context.setPassword(args[1]);
            }
            context.setCredentialsReceived(true);
        }
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public String getArgSplitRegEx() {
        return ",";
    }

}
