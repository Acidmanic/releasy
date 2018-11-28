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
package com.acidmanic.installation.tasks;

import com.acidmanic.installation.models.Scription;
import com.acidmanic.installation.utils.InstallationActions;
import com.acidmanic.utilities.StringParseHelper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class InstallJarFileExecutable extends InstallationTask<String, Void> {

    @Override
    protected boolean onWindows(String input) {

        return perform(input, "%*", ".bat");
    }

    @Override
    protected boolean onUnix(String input) {
        return perform(input, "$@", "");
    }

    private boolean perform(String input, String aArg, String sExt) {
        System.out.println("Installing " + input + sExt + " in env path.");
        String jarname = installDestinationJar();
        if (jarname == null) {
            return false;
        }
        Scription scr = getScription(input, jarname, aArg);
        return (new InstallationActions(getEnvironmentalInfo())
                .registerScript(scr, sExt)) != null;
    }

    private String installDestinationJar() {
        String myName = getEnvironmentalInfo()
                .getDeploymentMetadata().getExecutionJarFile()
                .getName();
        List<String> res = new ArrayList();
        new InstallationActions(getEnvironmentalInfo())
                .installContent(myName, res);
        if (res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }

    private Scription getScription(String input, String jarName, String allArgs) {
        String script = "java -jar ";
        script += new StringParseHelper().escapeAndQoute(jarName, '\"');
        script += " " + allArgs;
        Scription s = new Scription(script, input);
        return s;
    }

}
