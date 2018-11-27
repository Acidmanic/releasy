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
package com.acidmanic.release.releasestrategies;

import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.models.ReleaseParameters;
import com.acidmanic.release.versionables.Versionable;
import java.io.File;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class ReleaseIfAllPresentsSet implements ReleaseStrategy {

    @Override
    public void release(ReleaseParameters parameters) {
        File currentDirectory = new File(".");
        if (!parametersValid(parameters)) {
            return;
        }
        printVersionables(parameters.getVersionables());
        parameters.getReleaser().setup(currentDirectory, parameters.getReleaseType());
        if (!parameters.getReleaser().isPresent()) {
            Logger.log("WARNING: Release tool ("
                    + parameters.getReleaser().getClass().getSimpleName()
                    + ") is not present.");
            Logger.log("WARNING: Nothing has been done.");
            return;
        }
        if (setAll(parameters)) {
            Logger.log("INFO: 👍   All Versions set.");
            if (parameters.getReleaser().setVersion(parameters.getVersion())) {
                Logger.log("INFO: 👍   Released Successfully.");
            } else {
                Logger.log("ERROR: Final release did not succeed.");
            }

        } else {
            Logger.log("WARNING: Some present versionables encountered an error.");
            Logger.log("WARNING: while setting the version.");
            Logger.log("ERROR: Final Release will not be performed.");
        }
    }

    private boolean setAll(ReleaseParameters parameters) {
        boolean ret = parameters.getVersionables().size() > 0;
        for (Versionable versionable : parameters.getVersionables()) {
            ret = ret && versionable.setVersion(parameters.getVersion());
        }
        return ret;
    }

    private void printVersionables(List<Versionable> presents) {
        Logger.log("Found Versionable systems:");
        for (Versionable versionable : presents) {
            Logger.log("\t" + versionable.getClass().getSimpleName());
        }
    }


    private boolean parametersValid(ReleaseParameters parameters) {
        if (parameters == null) {
            return false;
        }
        if (parameters.getVersionables().isEmpty()) {
            Logger.log("WARNING: There is no known versionable system.");
            Logger.log("WARNING: Nothing has been done.");
            return false;
        }
        return true;
    }

}
