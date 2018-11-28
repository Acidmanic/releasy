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
package com.acidmanic.installation;

import com.acidmanic.installation.models.DeploymentMetadata;
import com.acidmanic.installation.tasks.InstallationTask;
import com.acidmanic.installation.environment.EnvironmentalInfo;
import com.acidmanic.installation.environment.EnvironmentalInfoProvider;
import com.acidmanic.installation.utils.Os;
import com.acidmanic.installation.environment.UnixEnvironmentalInfoProvider;
import com.acidmanic.installation.environment.WindowsEnvironmentalInfoProvider;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public abstract class InstallerBase {

    private DeploymentMetadata metadata;
    private EnvironmentalInfo environmentalInfo;

    private final List<InstallationTask> tasks;
    private final List<Boolean> results;

    protected abstract void setupMetaData(DeploymentMetadata metadata);

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public InstallerBase() {
        this.tasks = new ArrayList<>();
        this.results = new ArrayList<>();
        introduceTasks(tasks);
    }

    public void install() {
        this.metadata = new DeploymentMetadata();
        setupMetaData(metadata);
        environmentalInfo = getEnvironmentalInfoProvider().getInfo(metadata);

        runTasks();
    }

    private void runTasks() {
        Object input = null;
        for (InstallationTask task : this.tasks) {
            task.setEnvironmentalInfo(environmentalInfo);
            boolean result = task.execute(input);
            this.results.add(result);
            if (!result && !task.isIgnorable()) {
                break;
            }
            input = task.getResult();
        }
    }

    private EnvironmentalInfoProvider getEnvironmentalInfoProvider() {
        Os os = new Os();
        if (os.isWindows()) {
            return new WindowsEnvironmentalInfoProvider();
        } else if (os.isUnix()) {
            return new UnixEnvironmentalInfoProvider();
        }
        return (DeploymentMetadata metadata1) -> new EnvironmentalInfo();
    }

    protected abstract void introduceTasks(List<InstallationTask> tasks);

    public List<Boolean> getResults() {
        ArrayList<Boolean> ret = new ArrayList<>();
        ret.addAll(results);
        return ret;
    }

}
