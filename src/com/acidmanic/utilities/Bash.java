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
package com.acidmanic.utilities;

import static com.acidmanic.release.logging.Logger.log;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Bash{

    private interface LineScanner{
        void scan(String line);
    }
    
    public String syncRun(String command) {
        try {
            Process p = runCommand(command);
            StringBuilder builder= new StringBuilder();
            readOutputs(p,line -> builder.append(line).append("\n"));
            return builder.toString();
        } catch (Throwable th) {
            log("Runtime Error: " + th.getClass().getName());
        }
        return null;
    }

    private void readOutputs(Process p,LineScanner scanner) throws IOException {
        readAllInStream(p.getInputStream(),scanner);
        readAllInStream(p.getErrorStream(),scanner);
    }

    private void readAllInStream(InputStream input,LineScanner scanner) throws IOException {
        String line;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(input))) {
            while ((line = in.readLine()) != null) {
                scanner.scan(line);
            }
        }
    }

    private Process runCommand(String command) throws IOException {
        String[] splitedCommand = splitToArgs(command);
        Process p = Runtime.getRuntime().exec(splitedCommand);
        return p;
    }

    public ArrayList<String> syncRunLines(String command) {
        ArrayList<String> ret = new ArrayList<>();
        try {
            Process p = runCommand(command);
            readOutputs(p, line -> ret.add(line));
        } catch (Throwable th) {
            log("Runtime Error: " + th.getClass().getName());
        }
        return ret;
    }

    public void asyncRun(String Command) {
        log("Running Command " + Command);
        try {
            runCommand(Command);
        } catch (Throwable th) {
            log("Runtime Error: " + th.getClass().getName());
        }
    }

    public void asyncRun(String startupPath, String command) {
        log("Running Command " + command);
        try {
            Runtime.getRuntime().exec(splitToArgs(command), null, new File(startupPath));
        } catch (Throwable th) {
            log("Runtime Error: " + th.getClass().getName());
        }
    }

    private String[] splitToArgs(String command) {
        ArrayList<String> parts = new ArrayList<>();
        char[] chars = command.toCharArray();
        boolean inDoubleQoute = false;
        boolean inSingleQoute = false;
        String currentPart = "";
        char CHAR_SINGLE = "'".charAt(0);
        char CHAR_DOUBLE = "\"".charAt(0);
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (inSingleQoute) {
                if (ch == CHAR_SINGLE) {
                    inSingleQoute = false;
                    parts.add(currentPart);
                    currentPart = "";
                } else {
                    currentPart += ch;
                }
            } else if (inDoubleQoute) {
                if (ch == CHAR_DOUBLE) {
                    inDoubleQoute = false;
                    parts.add(currentPart);
                    currentPart = "";
                } else {
                    currentPart += ch;
                }
            } else if (ch == CHAR_DOUBLE) {
                inDoubleQoute = true;
            } else if (ch == CHAR_SINGLE) {
                inSingleQoute = true;
            } else if (Character.isWhitespace(ch)) {
                parts.add(currentPart);
                currentPart = "";
            } else {
                currentPart += ch;
            }
        }
        if (currentPart.length() > 0) {
            parts.add(currentPart);
        }
        String[] ret = new String[parts.size()];
        return parts.toArray(ret);
    }

}

