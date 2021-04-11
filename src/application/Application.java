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
package application;

import com.acidmanic.commandline.commands.Help;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.release.commands.Auto;
import com.acidmanic.release.commands.Bump;
import com.acidmanic.release.commands.Explain;
import com.acidmanic.release.commands.InstantRunTest;
import com.acidmanic.release.commands.Manual;
import com.acidmanic.release.commands.Status;
import com.acidmanic.release.readmeupdate.updaters.CarthageReadmeUpdater;
import com.acidmanic.release.readmeupdate.updaters.CocoapodsReadmeUpdater;
import com.acidmanic.release.readmeupdate.updaters.GradleReadmeUpdater;
import com.acidmanic.release.readmeupdate.updaters.MavenReadmeUpdater;
import com.acidmanic.release.readmeupdate.updaters.VisualStudio;
import com.acidmanic.release.releasestrategies.ReleaseIfAllPresentsSet;
import com.acidmanic.release.releasestrategies.ReleaseStrategy;
import com.acidmanic.release.sourcecontrols.JGitFacadeSourceControl;
import com.acidmanic.release.sourcecontrols.SourceControlSystem;
import com.acidmanic.release.versionables.GitTag;
import com.acidmanic.release.versionables.Maven;
import com.acidmanic.release.versionables.NodeJs;
import com.acidmanic.release.versionables.NuGetSpec;
import com.acidmanic.release.versionsources.NuGetDotnetCore;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versionables.XCode;
import com.acidmanic.release.versioncontrols.VersionControl;
import com.acidmanic.release.versions.SemanticVersionFactory;
import com.acidmanic.release.versions.VersionFactory;
import com.acidmanic.release.utilities.ClassRegistery;
import com.acidmanic.release.versionsources.Cocoapods;
import com.acidmanic.release.versionsources.JavaManifest;

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

    private static final TypeRegistery commandsRegistery = new TypeRegistery();

    public static void initialize() {
        
        // Version Source Files
        ClassRegistery.makeInstance().add(Cocoapods.class);
        ClassRegistery.makeInstance().add(Maven.class);
        ClassRegistery.makeInstance().add(XCode.class);
        ClassRegistery.makeInstance().add(NodeJs.class);
        ClassRegistery.makeInstance().add(NuGetSpec.class);
        ClassRegistery.makeInstance().add(NuGetDotnetCore.class);
        ClassRegistery.makeInstance().add(JavaManifest.class);
        ClassRegistery.makeInstance().add(VisualStudio.class);
        // Readme Updaters
        ClassRegistery.makeInstance().add(MavenReadmeUpdater.class);
        ClassRegistery.makeInstance().add(GradleReadmeUpdater.class);
        ClassRegistery.makeInstance().add(CarthageReadmeUpdater.class);
        ClassRegistery.makeInstance().add(CocoapodsReadmeUpdater.class);
        //SourceControl Systems
        ClassRegistery.makeInstance().add(JGitFacadeSourceControl.class);
        
        
        //Commands
        commandsRegistery.registerClass(Auto.class);
        commandsRegistery.registerClass(Manual.class);
        commandsRegistery.registerClass(Status.class);
        commandsRegistery.registerClass(Help.class);
        commandsRegistery.registerClass(Bump.class);
        commandsRegistery.registerClass(InstantRunTest.class);
        commandsRegistery.registerClass(Explain.class);

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

    public static TypeRegistery getCommandsRegistery() {
        return commandsRegistery;
    }

}
