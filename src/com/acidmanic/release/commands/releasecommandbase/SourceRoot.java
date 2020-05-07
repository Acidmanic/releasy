/*
 * Copyright (C) 2020 Acidmanic
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
package com.acidmanic.release.commands.releasecommandbase;

/**
 *
 * @author Acidmanic
 */
public class SourceRoot extends ReleaseArgumentCommandBase
        implements ReleaseParametersExecutionEnvironment.FixedArgument {

    @Override
    protected String getUsageString() {
        return "Sets the root directory for source control commits.";
    }

    @Override
    public void execute() {

        getExecutionEnvironment()
                .getDataRepository().set(ROOT, args[0]);
    }

    @Override
    public int numberOfArguments() {
        return 1;
    }

}
