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

import com.acidmanic.commandline.commands.ApplicationWideTypeRegistery;
import com.acidmanic.release.commands.Auto;
import com.acidmanic.release.commands.Install;
import com.acidmanic.release.commands.Manual;
import com.acidmanic.release.commands.Status;
import com.acidmanic.release.commands.Version;
import com.acidmanic.release.readmeupdate.updaters.CarthageReadmeUpdater;
import com.acidmanic.release.readmeupdate.updaters.CocoapodsReadmeUpdater;
import com.acidmanic.release.readmeupdate.updaters.GradleReadmeUpdater;
import com.acidmanic.release.readmeupdate.updaters.MavenReadmeUpdater;
import com.acidmanic.release.readmeupdate.updaters.VisualStudio;
import com.acidmanic.release.releasestrategies.ReleaseIfAllPresentsSet;
import release.inapptests.Test;
import com.acidmanic.release.releasestrategies.ReleaseStrategy;
import com.acidmanic.release.sourcecontrols.JGitFacadeSourceControl;
import com.acidmanic.release.sourcecontrols.SourceControlSystem;
import com.acidmanic.release.versionables.Cocoapods;
import com.acidmanic.release.versionables.GitTag;
import com.acidmanic.release.versionables.JavaManifest;
import com.acidmanic.release.versionables.Maven;
import com.acidmanic.release.versionables.NodeJs;
import com.acidmanic.release.versionables.NuGetSpec;
import com.acidmanic.release.versionables.NuGetDotnetCore;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versionables.XCode;
import com.acidmanic.release.versioncontrols.VersionControl;
import com.acidmanic.release.versions.SemanticVersionFactory;
import com.acidmanic.release.versions.VersionFactory;
import com.acidmanic.utilities.ClassRegistery;
import java.io.File;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Application {

    private static Versionable releaser;
    private static ReleaseStrategy releaseStrategy;
    private static VersionFactory versionFactory;
    private static SourceControlSystem sourceControlSystem;
    private static VersionControl versionControl;

    public static void initialize() {
        ClassRegistery.makeInstance().add(Cocoapods.class);
        ClassRegistery.makeInstance().add(Maven.class);
        ClassRegistery.makeInstance().add(XCode.class);
        ClassRegistery.makeInstance().add(NodeJs.class);
        ClassRegistery.makeInstance().add(NuGetSpec.class);
        ClassRegistery.makeInstance().add(NuGetDotnetCore.class);
        ClassRegistery.makeInstance().add(JavaManifest.class);
        ClassRegistery.makeInstance().add(VisualStudio.class);

        ClassRegistery.makeInstance().add(MavenReadmeUpdater.class);
        ClassRegistery.makeInstance().add(GradleReadmeUpdater.class);
        ClassRegistery.makeInstance().add(CarthageReadmeUpdater.class);
        ClassRegistery.makeInstance().add(CocoapodsReadmeUpdater.class);

        ApplicationWideTypeRegistery.makeInstance().registerClass(Manual.class);
        ApplicationWideTypeRegistery.makeInstance().registerClass(Auto.class);
        ApplicationWideTypeRegistery.makeInstance().registerClass(Test.class);
        ApplicationWideTypeRegistery.makeInstance().registerClass(Install.class);
        ApplicationWideTypeRegistery.makeInstance().registerClass(Status.class);
        ApplicationWideTypeRegistery.makeInstance().registerClass(Version.class);

        releaser = new GitTag();
        
        releaseStrategy = new ReleaseIfAllPresentsSet();

        versionFactory = new SemanticVersionFactory();

        sourceControlSystem = new JGitFacadeSourceControl();
        
        versionControl = new JGitFacadeSourceControl();
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

    public static SourceControlSystem getSourceControlSystem() {
        return sourceControlSystem;
    }

    public static VersionControl getVersionControl() {
        return versionControl;
    }

}
