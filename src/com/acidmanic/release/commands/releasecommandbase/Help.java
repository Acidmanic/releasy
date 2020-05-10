/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.commands.releasecommandbase;

/**
 *
 * @author diego
 */
public class Help extends com.acidmanic.commandline.commands.Help implements ReleaseParametersExecutionEnvironment.FixedArgument {

    @Override
    public int numberOfArguments() {
        return 0;
    }

}
