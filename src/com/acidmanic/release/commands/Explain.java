/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.commands;

import com.acidmanic.release.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versions.tools.VersionParser;

/**
 *
 * @author diego
 */
public class Explain extends ReleaseCommandBase {

    @Override
    protected void execute(VersionStandard standard, ReleaseWorkspace workspace, ReleaseContext subCommandsExecutionContext) {

        line(true);
        title("Standard");
        line(false);

        info("Name: " + standard.getName());
        
        VersionParser parser = new VersionParser(standard);

        String standardDescriptor = parser.getTemplate(false);

        info("Format: " + standardDescriptor);
        
        String defaultVersion = parser.getVersionString(parser.getZeroVersion());
        
        info("Default: " + defaultVersion);
        
        line(false);
        title("Files being checked");
        line(false);
        
        workspace.getVersionFilesScanner().scan(f -> f.isFile(),
                f -> info(f.toPath().toAbsolutePath().normalize().toString()));
        
        line(false);
        title("Source Control root");
        line(false);
        
        info(workspace.getSourceControlRoot().toPath().toAbsolutePath().normalize().toString());
        
        
        line(true);
    }

    @Override
    protected String getUsageDescription() {
        return "This command will explain common release-commands result.";
    }

    private void line(boolean heavy) {
        String line
                = heavy
                        ? "========================================"
                        : "----------------------------------------";
        warning(line);
    }

    private void title(String title) {
        info("\t\t" + title);
    }

}
