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
public class Inc extends CommandBase {

    @Override
    protected String getUsageDescription() {
        return "This argument specifies which segments of version must be "
                + "considered for incrementation.";
    }

    @Override
    protected String getArgumentsDesciption() {
        return "version segment names (comma separated) ex: minor,build";
    }

    @Override
    public void execute(String[] args) {
        ReleaseContext context = getContext();

        context.setIncrementSegmentNames(args);
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
