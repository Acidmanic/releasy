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
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versions.Version;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class ReleaseIfAllPresentsSet implements ReleaseStrategy {

    @Override
    public void release(List<Versionable> versionables, Versionable releaser, Version version) {
        File currentDirectory = new File(".");
        List<Versionable> presents = getPresents(versionables, currentDirectory);
        if (presents.isEmpty()) {
            Logger.log("WARNING: There is no known versionable system.");
            Logger.log("WARNING: Nothing has been done.");
            return;
        }
        printVersionables(presents);
        if (!releaser.isPresent()) {
            Logger.log("WARNING: Release tool is not present.");
            Logger.log("WARNING: Nothing has been done.");
            return;
        }
        if (setAll(presents, version)) {
            Logger.log("INFO: 👍 All Versions set.");
            if (releaser.setVersion(version)) {
                Logger.log("INFO: 👍 Released Successfully.");
            }else{
                Logger.log("ERROR: Final release did not succeed.");
            }

        } else {
            Logger.log("WARNING: Some present versionables encountered and error.");
            Logger.log("WARNING: while setting the version.");
            Logger.log("ERROR: Final Release will not be performed.");
        }
    }

    private boolean setAll(List<Versionable> presents, Version version) {
        boolean ret = presents.size() > 0;
        for (Versionable versionable : presents) {
            ret = ret && versionable.setVersion(version);
        }
        return ret;
    }

    private void printVersionables(List<Versionable> presents) {
        Logger.log("Found Versionable systems:");
        for (Versionable versionable : presents) {
            Logger.log(versionable.getClass().getSimpleName());
        }
    }

    private List<Versionable> getPresents(List<Versionable> versionables, File currentDirectory) {
        ArrayList<Versionable> ret = new ArrayList<>();
        for (Versionable versionable : versionables) {
            versionable.setDirectory(currentDirectory);
            if (versionable.isPresent()) {
                ret.add(versionable);
            }
        }
        return ret;
    }

}
