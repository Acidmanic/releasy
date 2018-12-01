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

import acidmanic.commandline.commands.ApplicationWideTypeRegistery;
import com.acidmanic.release.commands.Auto;
import com.acidmanic.release.commands.Install;
import com.acidmanic.release.commands.Manual;
import com.acidmanic.release.commands.Status;
import com.acidmanic.release.releasestrategies.ReleaseIfAllPresentsSet;
import release.inapptests.Test;
import com.acidmanic.release.releasestrategies.ReleaseStrategy;
import com.acidmanic.release.versionables.Cocoapods;
import com.acidmanic.release.versionables.GitTag;
import com.acidmanic.release.versionables.JavaManifest;
import com.acidmanic.release.versionables.Maven;
import com.acidmanic.release.versionables.NodeJs;
import com.acidmanic.release.versionables.NuGet;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versionables.XCode;
import com.acidmanic.release.versions.SemanticVersionFactory;
import com.acidmanic.release.versions.VersionFactory;
import com.acidmanic.utilities.ClassRegistery;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Application {

    private static Versionable releaser;
    private static ReleaseStrategy releaseStrategy;
    private static VersionFactory versionFactory;

    public static void initialize() {
        ClassRegistery.makeInstance().add(Cocoapods.class);
        ClassRegistery.makeInstance().add(Maven.class);
        ClassRegistery.makeInstance().add(XCode.class);
        ClassRegistery.makeInstance().add(NodeJs.class);
        ClassRegistery.makeInstance().add(NuGet.class);
        ClassRegistery.makeInstance().add(JavaManifest.class);

        ApplicationWideTypeRegistery.makeInstance().registerClass(Manual.class);
        ApplicationWideTypeRegistery.makeInstance().registerClass(Auto.class);
        ApplicationWideTypeRegistery.makeInstance().registerClass(Test.class);
        ApplicationWideTypeRegistery.makeInstance().registerClass(Install.class);
        ApplicationWideTypeRegistery.makeInstance().registerClass(Status.class);

        releaser = new GitTag();
        releaseStrategy = new ReleaseIfAllPresentsSet();

        versionFactory = new SemanticVersionFactory();
    }

    public static Versionable getReleaser() {
        return releaser;
    }

    public static ReleaseStrategy getReleaseStrategy() {
        return releaseStrategy;
    }

    public static VersionFactory getVersionFactory() {
        return versionFactory;
    }

    

}
