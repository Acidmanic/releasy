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

import com.acidmanic.release.logging.Logger;
import com.acidmanic.release.models.ReleaseParameters;
import com.acidmanic.release.versionables.GitTag;
import com.acidmanic.release.versions.SemanticVersion;
import com.acidmanic.release.versions.Version;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versions.ReleaseTypes;
import com.acidmanic.utilities.ClassRegistery;
import com.acidmanic.utilities.ReleaseParametersBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

        int type = ReleaseTypes.NIGHTLY;

        ReleaseParameters parameters = getParameters(version, type);

        printAllVersionsTest(parameters);

        presetupRelease(parameters);

        Application.getReleaseStrategy()
                .release(parameters);

    }

    private static void printAllVersionsTest(ReleaseParameters parameters) {
        Logger.log("------- Found Version Systems: -------------------");
        for (Versionable v : parameters.getVersionables()) {
            printVersionsByVersionable(v, parameters.getReleaseType());
        }
        printVersionsByVersionable(parameters.getReleaser(), parameters.getReleaseType());
        Logger.log("-------------------------------------------------");

    }

    private static void printVersionsByVersionable(Versionable v, int type) {
        v.setup(new File("."), type);
        List<String> versions = v.getVersions();
        Logger.log("", v);
        for (String vers : versions) {
            Logger.log(vers);
        }
    }

    private static List<Versionable> getPresentVersionables(File directory, int type) {
        List<Versionable> versionables = ClassRegistery.makeInstance()
                .all(Versionable.class
                );
        List<Versionable> ret = new ArrayList<>();
        for (Versionable v : versionables) {
            v.setup(directory, type);
            if (v.isPresent()) {
                try {
                    ret.add(
                            v.getClass().newInstance()
                    );
                } catch (Exception e) {
                }
            }
        }
        return ret;
    }

    private static ReleaseParameters getParameters(Version version, int type) {
        File here = new File(".");
        return new ReleaseParametersBuilder()
                .versionables(getPresentVersionables(here, type))
                .version(version)
                .releaser(new GitTag())
                .build();
    }

    private static void presetupRelease(ReleaseParameters parameters) {
        File currentDirectory = new File(".");
        if (!parametersValid(parameters)) {
            return;
        }
        printVersionables(parameters.getVersionables());
        parameters.getReleaser().setup(currentDirectory, parameters.getReleaseType());
        if (!parameters.getReleaser().isPresent()) {
            Logger.log("WARNING: Release tool ("
                    + parameters.getReleaser().getClass().getSimpleName()
                    + ") is not present.");
            Logger.log("WARNING: Nothing has been done.");
            return;
        }
    }

    private static boolean parametersValid(ReleaseParameters parameters) {
        if (parameters == null) {
            return false;
        }
        if (parameters.getVersionables().isEmpty()) {
            Logger.log("WARNING: There is no known versionable system.");
            Logger.log("WARNING: Nothing has been done.");
            return false;
        }
        return true;
    }

    private static void printVersionables(List<Versionable> presents) {
        Logger.log("Found Versionable systems:");
        for (Versionable versionable : presents) {
            Logger.log("\t" + versionable.getClass().getSimpleName());
        }
    }

}
