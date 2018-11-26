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
import com.acidmanic.release.versions.SemanticVersion;
import com.acidmanic.release.versions.Version;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.utilities.ClassRegistery;
import java.io.File;

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

        Application.initialize();

        String iden = "test-default";
        Version version = new SemanticVersion(2, 2, 2, iden);

        Application.getReleaseStrategy()
                .release(ClassRegistery.makeInstance().all(Versionable.class),
                        Application.getReleaser(), version);

    }

}
