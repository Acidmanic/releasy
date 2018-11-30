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

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public abstract class ReleaseIfAllPresentsSetBase extends ReleaseStrategyBase {

    protected boolean acceptReleaseOnly;

    public ReleaseIfAllPresentsSetBase() {
        acceptReleaseOnly = false;
    }

    @Override
    public void release(ReleaseParameters parameters) {

        if (setAll(parameters)) {
            log("INFO: 👍   All Versions set.");
            if (parameters.getReleaser().setVersion(parameters.getVersion())) {
                log("INFO: 👍   Released Successfully.");
            } else {
                Logger.log("ERROR: Final release did not succeed.");
            }

        } else {
            log("WARNING: Some present versionables encountered an error.");
            log("WARNING: while setting the version.");
            log("ERROR: Final Release will not be performed.");
        }
    }

    private boolean setAll(ReleaseParameters parameters) {
        boolean ret = parameters.getVersionables().size() > 0 || acceptReleaseOnly;
        for (Versionable versionable : parameters.getVersionables()) {
            ret = ret && versionable.setVersion(parameters.getVersion());
        }
        return ret;
    }

}
