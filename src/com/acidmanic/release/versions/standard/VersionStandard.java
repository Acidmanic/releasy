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
package com.acidmanic.release.versions.standard;

import java.util.ArrayList;

/**
 * This class is a model holding all information needed to introduce a version 
 * standard.
 * @author Acidmanic
 */
public class VersionStandard {
    
    private ArrayList<VersionSection> sections;
    private String name;

    public VersionStandard() {
        this.sections = new ArrayList<>();
    }

    public ArrayList<VersionSection> getSections() {
        return sections;
    }

    public void setSections(ArrayList<VersionSection> sections) {
        this.sections = sections;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
    
}
