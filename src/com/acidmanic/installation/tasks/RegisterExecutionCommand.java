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
import com.sun.javafx.runtime.SystemProperties;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class RegisterExecutionCommand extends InstallationTask<Scription, String> {

    @Override
    protected boolean onWindows(Scription input) {
        System.out.println("From Windows");
        String windir = System.getenv("windir");
        if (windir == null) {
            System.out.println("Unable to find windows directory.");
            return false;
        }
        File file = new File(windir).toPath().resolve(input.getName()+".bat").toFile();
        return registerScript(file, input);
    }

    @Override
    protected boolean onUnix(Scription input) {
        File file = new File("/usr/local/bin/").toPath().resolve(input.getName()).toFile();
        return registerScript(file, input);
    }

    private boolean registerScript(File file, Scription input) {
        try {
            if (file.exists()) {
                file.delete();
            }
            Files.write(file.toPath(), input.getScript().getBytes(DEFAULT_CHARSET),
                    StandardOpenOption.CREATE);
            file.setExecutable(true, false);
            this.result = file.getAbsolutePath();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);

        }
        return false;
    }

    @Override
    protected boolean getIgnorability() {
        return false;
    }

}
