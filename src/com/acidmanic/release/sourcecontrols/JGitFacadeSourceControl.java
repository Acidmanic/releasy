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
package com.acidmanic.release.sourcecontrols;

import com.acidmanic.release.versioncontrols.VersionControl;
import java.io.File;
import org.eclipse.jgit.api.Git;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class JGitFacadeSourceControl implements SourceControlSystem,VersionControl {

    @Override
    public void acceptLocalChanges(File directory, String description) {
        Git git = tryGetGit(directory);
        if (git != null) {
            try {
                git.add().addFilepattern(".").call();
                git.commit().setMessage(description).call();
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public boolean isPresent(File directory) {
        Git git = tryGetGit(directory);
        if(git!=null){
            try {
                git.status().call();
                return true;
            } catch (Exception e) {
            }
        }
        return false;
    }

    private Git tryGetGit(File directory) {
        try {
            return Git.open(directory);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void markVersion(File directory, String versionString, String message) {
        Git git = tryGetGit(directory);
        
        if(git!=null){
            String tag = normalizeForTag(versionString);
            
            try {
                
                git.tag().setName(tag).setMessage(message).call();
                
            } catch (Exception e) {}
        }
    }
    
    private String normalizeForTag(String value){
        
        value = value.replaceAll("\\s+", "-");
        
        return value;
    }

    @Override
    public boolean switchBranch(File directory,String name) {
        Git git = tryGetGit(directory);
        
        if(git!=null){
            
            try {
                git.checkout()
                        .setName(name)
                        .setCreateBranch(false)
                        .call();
                
                return true;
            } catch (Exception e) {}
        }
        return false;
    }

}
