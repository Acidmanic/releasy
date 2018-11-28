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
package com.acidmanic.installation.environment;

import com.acidmanic.installation.models.DeploymentMetadata;
import java.io.File;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class EnvironmentalInfo {

    private File executableBinariesDirectory;
    private File applicationsDirectory;
    private File installationDirectory;

    private final DeploymentMetadata deploymentMetadata;

    public EnvironmentalInfo(DeploymentMetadata deploymentMetadata) {
        this.deploymentMetadata = deploymentMetadata;
    }
    
    public File getExecutableBinariesDirectory() {
        return executableBinariesDirectory;
    }

    public void setExecutableBinariesDirectory(File executableBinariesDirectory) {
        this.executableBinariesDirectory = executableBinariesDirectory;
    }

    public File getInstallationDirectory() {
        return installationDirectory;
    }

    public void setInstallationDirectory(File installationDirectory) {
        this.installationDirectory = installationDirectory;
    }

    public File getApplicationsDirectory() {
        return applicationsDirectory;
    }

    public void setApplicationsDirectory(File applicationsDirectory) {
        this.applicationsDirectory = applicationsDirectory;
    }

    public DeploymentMetadata getDeploymentMetadata() {
        return deploymentMetadata;
    }

}
