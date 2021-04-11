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

import com.acidmanic.release.Releaser;
import com.acidmanic.release.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.versions.standard.VersionStandard;
import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class Increment extends GitbumpStepBase{
    
    private final String incrementingSectionName;

    public Increment(String incrementingSectionName) {
        this.incrementingSectionName = incrementingSectionName;
    }
    
    @Override
    protected boolean performExecution(Context context) {
        
        ArrayList<String> changes = new ArrayList<>();
        
        changes.add(incrementingSectionName);
        
        ReleaseWorkspace workspace = context.getWorkspace();
        
        VersionStandard standard = context.getStandard();
        
        Releaser releaser = new Releaser(workspace, standard);
        
        boolean ret = releaser.release(changes);
        
        return ret;
    }
    
}
