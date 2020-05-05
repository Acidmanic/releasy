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
package com.acidmanic.release.commands;

import com.acidmanic.commandline.commands.CommandBase;
import com.acidmanic.commandline.commands.parameters.ParameterBuilder;
import com.acidmanic.consoletools.table.builders.TableBuilder;
import com.acidmanic.release.Releaser;
import com.acidmanic.release.environment.ReleaseEnvironment;
import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.readmeupdate.ReadMeVersionSet;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versions.Change;

import static com.acidmanic.release.versions.ReleaseTypes.ALPHA;
import static com.acidmanic.release.versions.ReleaseTypes.BETA;
import static com.acidmanic.release.versions.ReleaseTypes.NIGHTLY;
import static com.acidmanic.release.versions.ReleaseTypes.RELEASE_CANDIDATE;
import static com.acidmanic.release.versions.ReleaseTypes.STABLE;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versionstandard.StandardProvider;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public abstract class ReleaseBase extends CommandBase {

    protected final static String RELEASE_TYPE_ARG_DEC = "<release-type>";
    protected final static String RELEASE_TYPE_DESCRIPTION
            = "<release-type> will describe the level "
            + "of development for your release. it can be nightly (default),"
            + "alpha, beta, release-candidate (alternatively: rc) or stable.";

    private VersionStandard standard;

    public ReleaseBase() {
        this.standard = provideStandard();
    }
    
    

    protected VersionStandard getStandard() {

        return this.standard;
    }

    @Override
    public void execute() {

        this.standard = provideStandard();
    }

    private int releaseType;

    public void setArgs(String[] args) {
        this.args = args;
        this.releaseType = getReleaseType();
    }

    @Override
    protected void defineParameters(ParameterBuilder builder) {
        builder.described("This parameter is the name of the version standard being used "
                + "with current command. This standard can be a built-in standard "
                + "within the application or a standard from a version standard "
                + "json file alongside with the application executable file.")
                .named("version-standard").ofType(String.class).optional();
    }

    private Releaser makeReleaser() {
        Releaser r = new Releaser(new ReleaseEnvironment().getDirectory());

        r.setAfterVersionSelect((com.acidmanic.release.versions.Version v)
                -> {
            log("INFO: Setting version to: " + v.getVersionString());
            new ReadMeVersionSet().setVersion(v, releaseType);
        });

        r.setAfterVersionSet((HashMap<Versionable, Boolean> hash) -> printResultTable(hash));

        return r;
    }

    protected void release(ArrayList<String> changes) {

        Releaser releaser = makeReleaser();

        boolean success = releaser.release(standard, changes);
        
        logRelease(success);
    }

    /**
     * Auto create version
     */
    @Deprecated
    protected void release(Change change) {
        logRelease(makeReleaser().release(releaseType, change));
    }
    @Deprecated
    protected void release(com.acidmanic.release.versions.Version version) {
        logRelease(makeReleaser().release(releaseType, version));
    }

    @Deprecated
    private int getReleaseType() {
        String[] names = {"nightly", "alpha", "beta", "release-candidate", "rc", "stable"};
        int[] values = {NIGHTLY, ALPHA, BETA, RELEASE_CANDIDATE, RELEASE_CANDIDATE, STABLE};
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            if (apearedInArgs(name)) {
                return values[i];
            }
        }
        return NIGHTLY;
    }

    private boolean apearedInArgs(String name) {
        for (String arg : args) {
            if (name.compareTo(arg.toLowerCase()) == 0) {
                return true;
            }
        }
        return false;
    }

    private void printResultTable(HashMap<Versionable, Boolean> hash) {

        TableBuilder builder = new TableBuilder();

        for (Versionable versionable : hash.keySet()) {
            Boolean result = hash.get(versionable);

            builder.row()
                    .cell(versionable.getClass().getSimpleName())
                    .maximumWidth(30)
                    .cell(result ? "🆗 Done" : "🚫 Failed")
                    .maximumWidth(20);
        }

        builder.tableBorder().marginAll(1, 0);

        Logger.log(builder.build().render());
    }

    private void logRelease(boolean release) {
        if (release) {
            Logger.log(" 👍    Successfully released.");
        } else {
            Logger.log(" 🧐    Release has not been completed..");
        }
        Logger.log("");
    }

    protected VersionStandard provideStandard() {

        String name = getParameterValue("version-standard");

        if (name == null) {
            name = "semantic";
        }
        VersionStandard ret = new StandardProvider().getStandard(name);

        return ret;
    }

}
