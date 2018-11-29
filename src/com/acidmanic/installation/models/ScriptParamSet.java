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
package com.acidmanic.installation.models;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class ScriptParamSet {

    public String header;
    public String fileExtension;
    public String allArgumentsSymbol;
    public String newLineSymbol;

    public ScriptParamSet() {
        this.header = "";
        this.fileExtension = "";
        this.allArgumentsSymbol = "$@";
        this.newLineSymbol = "\n";
    }

    public ScriptParamSet(String header, String fileExtension, String allArgumentsSymbol, String newLineSymbol) {
        this.header = header;
        this.fileExtension = fileExtension;
        this.allArgumentsSymbol = allArgumentsSymbol;
        this.newLineSymbol = newLineSymbol;
    }

}
