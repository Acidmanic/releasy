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
import com.acidmanic.release.models.ScriptParamSet;
import com.acidmanic.release.models.ScriptParamSets;
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

        return perform(input, ScriptParamSets.WINDOWS_ONPATH_SCRIPT);
    }

    @Override
    protected boolean onUnix(String input) {
        return perform(input, ScriptParamSets.UNIX_ONPATH_SCRIPT);
    }

    private boolean perform(String scriptname, ScriptParamSet params) {
        System.out.println("Installing " + scriptname + params.fileExtension + " in env path.");
        String jarname = installDestinationJar();
        if (jarname == null) {
            return false;
        }
        Scription scr = getScription(scriptname, jarname, params);
        return (new InstallationActions(getEnvironmentalInfo())
                .registerScript(scr, params.fileExtension)) != null;
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

    private Scription getScription(String scriptname,
            String jarName,
            ScriptParamSet params) {
        String script = params.header + params.newLineSymbol;
        script += "java -jar ";
        script += new StringParseHelper().escapeAndQoute(jarName, '\"');
        script += " " + params.allArgumentsSymbol;
        Scription s = new Scription(script, scriptname);
        return s;
    }

}
