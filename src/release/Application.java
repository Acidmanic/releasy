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

import com.acidmanic.release.releasestrategies.ReleaseIfAllPresentsSet;
import com.acidmanic.release.releasestrategies.ReleaseStrategy;
import com.acidmanic.release.versionables.Cocoapods;
import com.acidmanic.release.versionables.GitTag;
import com.acidmanic.release.versionables.Maven;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versionables.XCode;
import com.acidmanic.utilities.ClassRegistery;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Application {
    
    
    private static Versionable releaser;
    private static ReleaseStrategy releaseStrategy;
    
    public static void initialize(){
        ClassRegistery.makeInstance().add(Cocoapods.class);
        ClassRegistery.makeInstance().add(Maven.class);
        ClassRegistery.makeInstance().add(XCode.class);
        
        
        releaser = new GitTag();
        releaseStrategy = new ReleaseIfAllPresentsSet();
    }

    public static Versionable getReleaser() {
        return releaser;
    }

    public static ReleaseStrategy getReleaseStrategy() {
        return releaseStrategy;
    }
    
    
}