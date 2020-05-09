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
package com.acidmanic.release.commands;

import com.acidmanic.commandline.application.ExecutionDataRepository;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.release.Releaser2;
import com.acidmanic.release.commands.directoryscanning.ReleaseWorkspace;
import com.acidmanic.release.commands.releasecommandbase.ReleaseCommandBase2;
import com.acidmanic.release.versions.standard.VersionStandard;

/**
 *
 * @author Acidmanic
 */
public class Manual2 extends ReleaseCommandBase2{

    @Override
    protected void execute(VersionStandard standard, ReleaseWorkspace workspace, ExecutionDataRepository dataRepository) {
        
        
        String version = dataRepository.get(com.acidmanic.release.commands.releasecommandbase.Version.VERSION_STRING);
        
        Releaser2 releaser = new Releaser2(workspace, standard);
        
        releaser.setVersionToWorkspace(version);
    }

    @Override
    protected String getUsageString() {
        return "";
    }

    @Override
    protected void registerArguments(TypeRegistery registery) {
        super.registerArguments(registery); 
        
        registery.registerClass(com.acidmanic.release.commands.releasecommandbase.Version.class);
    }
    
    
    
}
