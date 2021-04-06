/*
 * Copyright (C) 2020 Acidmanic
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

import com.acidmanic.release.utilities.Emojies;

/**
 *
 * @author Acidmanic
 */
public class GrantResult {
    
    private boolean grant;
    
    private String message;

    public GrantResult() {
        
        this.message ="";
        
        this.grant = false;
    }

    public GrantResult(boolean grant, String message) {
        this.grant = grant;
        this.message = message;
    }
    
    public void log(String text){
        this.message += "\n" + text;
    }
    
    public void info(String text){
        log(Emojies.Symbols.INFORMATION + " " + text);
    }
    
    public void warning(String text){
        log(Emojies.Symbols.WARNING + " " + text);
    }
    
    public void error(String text){
        log(Emojies.Symbols.NO_ENTRY + " " + text);
    }

    public boolean isGrant() {
        return grant;
    }

    public void setGrant(boolean grant) {
        this.grant = grant;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
    
}
