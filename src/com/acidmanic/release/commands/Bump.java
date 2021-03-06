/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.commands;

import com.acidmanic.release.commands.arguments.IncrementInputAnalyzer;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.release.ReleaseResult;
import com.acidmanic.release.Releaser;
import com.acidmanic.release.commands.arguments.Auth;
import com.acidmanic.release.commands.arguments.Inc;
import com.acidmanic.release.directoryscanning.MergeArguments;
import com.acidmanic.release.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.commands.arguments.Merge;
import com.acidmanic.release.environment.SourceControlSystemInspector;
import com.acidmanic.release.sourcecontrols.SourceControlSystem;
import com.acidmanic.release.versions.standard.VersionStandard;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class Bump extends ReleaseCommandBase {

    @Override
    protected void execute(VersionStandard standard, ReleaseWorkspace workspace, ReleaseContext subCommandsExecutionContext) {

        Releaser releaser = new Releaser(workspace, standard);

        if (subCommandsExecutionContext.isCredentialsReceived()) {
            releaser.setCredentials(subCommandsExecutionContext.getUsername(),
                    subCommandsExecutionContext.getPassword());
        }
        
        List<String> changes = new IncrementInputAnalyzer().extractChanges(standard, subCommandsExecutionContext.getIncrementSegmentNames());

        ReleaseResult releaseResult = releaser.release(changes);

        if (releaseResult.isSuccessful()) {
            List<SourceControlSystem> presentSourceControls
                    = new SourceControlSystemInspector(workspace.getSourceControlRoot())
                            .getPresentSourceControlSystems();

            File root = workspace.getSourceControlRoot();

            MergeArguments mergeArguments = subCommandsExecutionContext.getMergeArguments();

            for (SourceControlSystem sourceControl : presentSourceControls) {

                if (subCommandsExecutionContext.isCredentialsReceived()) {
                    sourceControl.setCredentials(subCommandsExecutionContext.getUsername(),
                            subCommandsExecutionContext.getPassword());
                }

                boolean success = performMerge(root, sourceControl, mergeArguments);

                if (success) {

                    success = pushAll(root, sourceControl, mergeArguments)
                            && releaseResult.getUpdateSourceControlRemote().invoke();

                    if (success) {

                        info("bump has been done perfectly");

                    } else {
                        //TODO: you might want to set a retrying mechanisem here
                        failApplication("Error Pushing merged branches.");
                    }
                } else {
                    failApplication("Error merging given branches.");
                }
            }
        } else {
            failApplication("Unable to Update new version in workspace.");
        }

    }

    @Override
    protected String getUsageDescription() {
        return "This command will first increment version based on given changes."
                + " Then it tries to merge given branches and if merge was "
                + "fully successful, it will push branches back using source "
                + "control systems whom are present in workspace.";
    }

    @Override
    protected void addArgumentClasses(TypeRegistery registery) {
        super.addArgumentClasses(registery);

        registery.registerClass(Merge.class);
        registery.registerClass(Inc.class);
        registery.registerClass(Auth.class);
    }

    private boolean performMerge(File root, SourceControlSystem sourceControl, MergeArguments mergeArguments) {

        String source = mergeArguments.getSource();

        for (String destination : mergeArguments.getDestinations()) {

            if (!sourceControl.switchBranch(root, destination)
                    || !sourceControl.mergeBranchIntoCurrent(root, source)) {

                return false;
            }

        }
        return true;
    }

    private boolean pushAll(File root, SourceControlSystem sourceControl, MergeArguments mergeArguments) {

        List<String> updateBranches = new ArrayList<>();

        updateBranches.add(mergeArguments.getSource());

        updateBranches.addAll(mergeArguments.getDestinations());

        boolean success = true;

        for (String branchName : updateBranches) {

            success = success && sourceControl.updateRemote(root, branchName);
        }
        return success;
    }

}
