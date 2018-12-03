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

import acidmanic.commandline.commands.CommandBase;
import com.acidmanic.release.Releaser;
import com.acidmanic.release.readmeupdate.ReadMeVersionSet;

import static com.acidmanic.release.versions.ReleaseTypes.ALPHA;
import static com.acidmanic.release.versions.ReleaseTypes.BETA;
import static com.acidmanic.release.versions.ReleaseTypes.NIGHTLY;
import static com.acidmanic.release.versions.ReleaseTypes.RELEASE_CANDIDATE;
import static com.acidmanic.release.versions.ReleaseTypes.STABLE;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public abstract class ReleaseBase extends CommandBase {

   

    @Override
    public void execute() {

        Releaser r = new Releaser();

        int releaseType = getReleaseType();
        
        r.setAfterVersionsSet((com.acidmanic.release.versions.Version v)
                -> new ReadMeVersionSet().setVersion(v, releaseType));

        r.release(releaseType);
    }

    protected int getReleaseType() {
        String[] names = {"nightly", "alpha", "beta", "release-candidate", "rc", "stable"};
        int[] values = {NIGHTLY, ALPHA, BETA, RELEASE_CANDIDATE, RELEASE_CANDIDATE, STABLE};
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            if (apearedInArgs(name)) {
                return values[i];
            }
        }
        return NIGHTLY;
    }

    private boolean apearedInArgs(String name) {
        for (String arg : args) {
            if (name.compareTo(arg.toLowerCase()) == 0) {
                return true;
            }
        }
        return false;
    }

}
