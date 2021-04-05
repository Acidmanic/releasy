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
package com.acidmanic.release.commands;

import com.acidmanic.commandline.commands.CommandBase;
import com.acidmanic.installation.BasicInstaller;
import com.acidmanic.installation.InstallerBase;
import com.acidmanic.installation.models.DeploymentMetadata;
import com.acidmanic.installation.tasks.InstallJarFileExecutable;
import com.acidmanic.installation.tasks.InstallationTask;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Install extends CommandBase {

    @Override
    protected String getUsageString() {
        return "";
    }

    private class Installer extends InstallerBase {

        @Override
        protected void setupMetaData(DeploymentMetadata metadata) {
            metadata.setOrganizationName("Acidmanic");
            metadata.setProductName("Releasy");
        }

        @Override
        protected void introduceTasks(List<InstallationTask> tasks) {
            tasks.add(new InstallJarFileExecutable());
        }

    }

    @Override
    public void execute() {
        new Installer().install();
    }

    @Override
    public boolean isVisible() {
        return false;
    }

}
