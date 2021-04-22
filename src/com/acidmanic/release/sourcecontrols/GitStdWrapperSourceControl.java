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
package com.acidmanic.release.sourcecontrols;

import com.acidmanic.lightweight.logger.ConsoleLogger;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.release.utilities.GitStdWrapper;
import java.io.File;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class GitStdWrapperSourceControl implements SourceControlSystem {

    private final Logger logger;

    private boolean useCredentials = false;
    private String username;
    private String password;

    public GitStdWrapperSourceControl(Logger logger) {
        this.logger = logger;
    }

    public GitStdWrapperSourceControl() {
        this.logger = new ConsoleLogger();
    }

    @Override
    public void acceptLocalChanges(File directory, String description) {
        GitStdWrapper git = new GitStdWrapper(directory);
        git.addAll();
        git.commit(description);
    }

    @Override
    public boolean isPresent(File directory) {
        return new GitStdWrapper(directory).isGitRepository();
    }

    @Override
    public boolean switchBranch(File directory, String name) {
        GitStdWrapper git = new GitStdWrapper(directory);
        git.command("checkout " + name);
        return true;
    }

    @Override
    public boolean mergeBranchIntoCurrent(File directory, String branchName) {
        this.logger.error(this.getClass().getSimpleName()
                + " does not implement merge operation.");
        return false;
    }

    @Override
    public boolean updateRemote(File directory, String branchName) {
        this.logger.error(this.getClass().getSimpleName()
                + " does not implement push operation.");
        return false;
    }

    @Override
    public boolean updateLocal(File directory, String branchName) {
        this.logger.error(this.getClass().getSimpleName()
                + " does not implement pull operation.");
        return false;
    }

    @Override
    public void setCredentials(String username, String password) {

        this.username = username;
        this.password = password;
        this.useCredentials = true;
    }

    @Override
    public void resetCredentials() {
        this.username = null;
        this.password = null;
        this.useCredentials = false;
    }

}
