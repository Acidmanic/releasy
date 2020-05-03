/*
 * Copyright (C) 2020 Acidmanic
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
package com.acidmanic.release.playgrounds;

import com.acidmanic.release.fileeditors.JsonEditor;
import com.acidmanic.release.test.TestVersionStandardGenerator;
import com.acidmanic.release.versions.standard.VersionStandard;
import com.acidmanic.release.versions.standard.VersionStandards;
import com.acidmanic.release.versions.tools.VersionParser;
import com.acidmanic.release.versionstandard.StandardProvider;
import com.acidmanic.utilities.ApplicationInfo;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Acidmanic
 */
public class VersionStandardPlayground {

    public static void main(String[] args) throws IOException {

        File file = new ApplicationInfo().getExecutionDirectory()
                .toPath().resolve("testandard.json").toFile();

        JsonEditor j = new JsonEditor(file);

        VersionStandard testandard = new TestVersionStandardGenerator().makeTestandard();

        j.save(testandard);

        file = new File("semantic.json");

        j = new JsonEditor(file);

        j.save(VersionStandards.SIMPLE_SEMANTIC);

        VersionParser parser = new VersionParser(VersionStandards.SIMPLE_SEMANTIC);

        System.out.println("As Version: " + parser.getTemplate(false));

        System.out.println("As Tag: " + parser.getTemplate(true));

        StandardProvider provider = new StandardProvider();

        List<String> standardNames = provider.availableStandards();

        standardNames.forEach(name -> System.out.println("Found: " + name
                + " Tag Template: " + new VersionParser(provider.getStandard(name))
                        .getTemplate(true)));

    }
}
