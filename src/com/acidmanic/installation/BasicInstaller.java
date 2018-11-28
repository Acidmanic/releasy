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
package com.acidmanic.installation;

import com.acidmanic.installation.tasks.DataFeed;
import com.acidmanic.installation.tasks.InstallApplicationContent;
import com.acidmanic.installation.tasks.InstallJarFileExecutable;
import com.acidmanic.installation.tasks.InstallationTask;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public abstract class BasicInstaller extends InstallerBase{

    @Override
    protected void introduceTasks(List<InstallationTask> tasks) {
        
        ArrayList<String> content = new ArrayList<>();
        content.add("lib");
        
        tasks.add( new DataFeed<>(content));
        tasks.add(new InstallApplicationContent());
        tasks.add(new DataFeed<>("release"));
        tasks.add(new InstallJarFileExecutable());
        
        
    }

    
    
    
    
}
