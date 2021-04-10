/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.commands.arguments;

import com.acidmanic.commandline.commands.CommandBase;
import com.acidmanic.release.commands.ReleaseContext;
import com.acidmanic.release.directoryscanning.MergeArguments;

/**
 *
 * @author diego
 */
public class Merge extends CommandBase {

    @Override
    protected String getUsageDescription() {
        return "This parameter takes 2 or more branche names. the first one "
                + "would be the source branch and the following names would be "
                + "destination branch names for source branch to be merged into."
                + "ex: hotfix-1238/fix-null-exception,master,develop";
    }

    @Override
    protected String getArgumentsDesciption() {
        return "";
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 1) {

            MergeArguments mergeArguments = new MergeArguments();

            mergeArguments.setSource(args[0]);

            for (int i = 1; i < args.length; i++) {
                mergeArguments.getDestinations().add(args[i]);
            }

            ReleaseContext context = getContext();

            context.setMergeArguments(mergeArguments);

        } else {
            error("List of branch names with atleat 2 items was expected.");
        }
    }

    @Override
    public String getArgSplitRegEx() {
        return ",";
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

}
