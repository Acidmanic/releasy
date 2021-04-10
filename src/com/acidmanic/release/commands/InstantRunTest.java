/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.commands;

import com.acidmanic.commandline.commands.CommandBase;
import com.acidmanic.release.environment.SourceControlSystemInspector;
import com.acidmanic.release.sourcecontrols.SourceControlSystem;
import java.io.File;
import java.util.List;

/**
 *
 * @author diego
 */
public class InstantRunTest extends CommandBase{

    @Override
    protected String getUsageDescription() {
        return "No one uses it.";
    }

    @Override
    public boolean accepts(String name) {
        return  "test".equals(name) || super.accepts(name); 
    }
    
    

    @Override
    protected String getArgumentsDesciption() {
        return "";
    }

    @Override
    public void execute(String[] args) {
        
        List<SourceControlSystem> presentSourceControls
                    = new SourceControlSystemInspector(
                            new File(".").toPath().toAbsolutePath().normalize().toFile())
                            .getPresentSourceControlSystems();
        
        
    }

    @Override
    public boolean hasArguments() {
        return false;
    }

    @Override
    public boolean isVisible() {
        return false;
    }
    
    
    
}
