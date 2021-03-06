/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.commands;

import com.acidmanic.commandline.commands.context.ExecutionContext;
import com.acidmanic.release.directoryscanning.DirectoryScannerBundle;
import com.acidmanic.release.directoryscanning.MergeArguments;
import com.acidmanic.release.directoryscanning.ReleaseWorkspace;
import java.io.File;

/**
 *
 * @author diego
 */
public class ReleaseContext implements ExecutionContext {

    private String standardName;
    private ReleaseWorkspace workspace;
    private DirectoryScannerBundle bundle;
    private File root;
    private String versionString;
    private String[] incrementSegmentNames = {};
    private MergeArguments mergeArguments;
    private String username;
    private String password;
    private boolean credentialsReceived;

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public ReleaseWorkspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(ReleaseWorkspace workspace) {
        this.workspace = workspace;
    }

    public DirectoryScannerBundle getBundle() {
        return bundle;
    }

    public void setBundle(DirectoryScannerBundle bundle) {
        this.bundle = bundle;
    }

    public File getRoot() {
        return root;
    }

    public void setRoot(File root) {
        this.root = root;
    }

    public String getVersionString() {
        return versionString;
    }

    public void setVersionString(String versionString) {
        this.versionString = versionString;
    }

    public String[] getIncrementSegmentNames() {
        return incrementSegmentNames;
    }

    public void setIncrementSegmentNames(String[] incrementSegmentNames) {
        this.incrementSegmentNames = incrementSegmentNames;
    }

    public MergeArguments getMergeArguments() {
        return mergeArguments;
    }

    public void setMergeArguments(MergeArguments mergeArguments) {
        this.mergeArguments = mergeArguments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCredentialsReceived() {
        return credentialsReceived;
    }

    public void setCredentialsReceived(boolean credentialsReceived) {
        this.credentialsReceived = credentialsReceived;
    }

}
