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
package release;

import com.acidmanic.release.versionables.Cocoapods;
import com.acidmanic.release.versionables.GitTag;
import com.acidmanic.release.versionables.Maven;
import com.acidmanic.release.versionables.XCode;
import com.acidmanic.release.versioning.SemanticVersion;
import com.acidmanic.release.versioning.Version;
import com.acidmanic.release.versioning.Versionable;
import java.io.File;
import java.util.Date;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Release {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        File here = new File(".");
        
        Versionable setter = new Cocoapods();
        String iden = "testgit2";
        Version version = new SemanticVersion(1, 2, 3, iden);
        
        setter.setDirectory(here);
        setter.setVersion(version);
        
        setter = new XCode();
        setter.setDirectory(here);
        setter.setVersion(version);
        
        setter = new Maven();
        setter.setDirectory(here);
        setter.setVersion(version);
        
        setter = new GitTag();
        setter.setDirectory(here);
        setter.setVersion(version);
        
        
        
    }
    
}
