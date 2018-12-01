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
import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.utilities.ClassRegistery;
import java.util.List;
import release.Application;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Version extends CommandBase {

    @Override
    protected String getUsageString() {
        return "Shows application version and related info.";
    }

    @Override
    public void execute() {
        String sVersion = this.getClass().getPackage().getImplementationVersion();
        if (sVersion == null || sVersion.isEmpty()) {
            sVersion = Application.getVersionFactory().blank().getVersionString();
        }
        Logger.log("Acidmanic Releasy");
        Logger.log("");
        Logger.log("Version: " + sVersion);
        Logger.log("");
        Logger.log("Supported Versionable Systems:");
        Logger.log("");
        List<Versionable> allVersionables
                = ClassRegistery.makeInstance().all(Versionable.class);
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (Versionable v : allVersionables) {
            sb.append(v.getClass().getSimpleName()).append(sep);
            sep = ", ";
        }
        Logger.log(sb.toString());
        Logger.log("");

    }

}
