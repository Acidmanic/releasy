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
public abstract class EnvironmentalInfoProviderBase implements EnvironmentalInfoProvider {

    @Override
    public EnvironmentalInfo getInfo(DeploymentMetadata metadata) {
        EnvironmentalInfo ret = new EnvironmentalInfo();
        ret.setExecutableBinariesDirectory(executableBinariesDirectory());
        ret.setApplicationsDirectory(applicationsDirectory());
        ret.setInstallationDirectory(getInstallDir(ret.getApplicationsDirectory(), metadata));
        return ret;
    }

    private File getInstallDir(File applicationsDirectory, DeploymentMetadata metadata) {
        return applicationsDirectory.toPath()
                .resolve(metadata.getOrganizationName())
                .resolve(metadata.getProductName())
                .toFile();
    }

    protected abstract File executableBinariesDirectory();

    protected abstract File applicationsDirectory();

}
