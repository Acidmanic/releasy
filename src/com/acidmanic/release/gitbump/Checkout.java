/*
 * Copyright (C) 2020 diego
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
package com.acidmanic.release.gitbump;

import com.acidmanic.release.sourcecontrols.JGitFacadeSourceControl;
import java.io.File;

/**
 *
 * @author diego
 */
public class Checkout extends GitbumpStepBase {

    private final String branchName;

    public Checkout(String branchName) {

        this.branchName = branchName;
    }

    @Override
    protected boolean performExecution(Context context) {
        File directory = context.getDirectory();

        JGitFacadeSourceControl jgit = new JGitFacadeSourceControl();

        boolean result = jgit.switchBranch(directory, this.branchName);

        return result;
    }

}
