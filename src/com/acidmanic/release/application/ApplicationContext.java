/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.application;

import com.acidmanic.commandline.commands.context.ExecutionContext;

/**
 *
 * @author diego
 */
public class ApplicationContext implements ExecutionContext{
    
    
    private boolean executionSuccess = true;
    private String failureMessage = "";
    
    public boolean getExecutionSuccess() {
        return executionSuccess;
    }

    public void fail(){
        this.executionSuccess = false;
    }
    
    public void fail(String reason){
        this.executionSuccess = false;
        this.failureMessage += reason + "\n";
    }
    
    public String getFailureMessage(){
        return this.failureMessage;
    }
    
}
