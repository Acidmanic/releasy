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
import java.util.ArrayList;
import java.util.List;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import release.Application;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Test extends CommandBase {

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    protected String getUsageString() {
        return "";
    }

    @Override
    public void execute() {
        JUnitCore core = new JUnitCore();
        List<Class> classes = getClasses();
        log("");
        for (Class c : classes) {
            logLine();
            String shortName = c.getSimpleName();
            shortName = shortName.substring(0, shortName.indexOf("Test"));
            log(" 🔧    Running Unit Tests for: " + shortName);
            log("");
            Result result = core.run(c);
            result = winFilter(result);
            log(result);
        }
        logLine();
    }

    private void logLine() {
        log("------------------------------------------------------------------");
    }

    private void log(Result result) {
        logLine();
        log(" " + resultTitle(result));
        result.getFailures().forEach((Failure f) -> {
            String name = getFailureName(f);
            logLine();
            log(" ❌   " + name);
            log("    " + f.getMessage());
            log("");
        });
    }

    private boolean isSucceeded(Result result) {
        boolean success = result.wasSuccessful() || (result.getFailureCount() == 0);
        return success;
    }

    private String getFailureName(Failure f) {
        String name = f.getTestHeader();
        name = name.substring(0, name.indexOf("("));
        return name;
    }

    private List<Class> getClasses() {
        ArrayList<Class> ret = new ArrayList<>();
        for (String name : args) {
            try {
                Class type = Class.forName(Application.getInAppTestPackage()
                        + "." + name + "Test");
                ret.add(type);
            } catch (Exception e) {
            }
        }
        return ret;
    }

    private Result winFilter(Result result) {
        List<Failure> failures = new ArrayList<>();
        result.getFailures().forEach((Failure f) -> {
            if (winValidFailure(f)) {
                failures.add(f);
            }
        });
        result.getFailures().clear();
        result.getFailures().addAll(failures);
        return result;
    }

    private boolean winValidFailure(Failure f) {
        if (f == null) {
            return false;
        }
        if (f.getMessage() == null) {
            return false;
        }

        return true;
    }

    private String resultTitle(Result result) {
        boolean success = isSucceeded(result);
        String suc = "👍    All Passed.";
        String fail = "❌    Failures: ("
                + result.getFailureCount() + ")";
        return (success ? suc : fail);
    }

}
