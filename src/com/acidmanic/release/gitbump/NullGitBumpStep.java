/*
 * Copyright (C) 2020 diego
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
package com.acidmanic.release.gitbump;

/**
 *
 * @author diego
 */
public class NullGitBumpStep implements GitBumpStep{

    @Override
    public void execute(Context context) {
        System.out.println("Given Step did not match with any known gitbump step.");
    }

    @Override
    public String getId() {
        return "NULL";
    }

    @Override
    public void setId(String id) {
    }
    
}
