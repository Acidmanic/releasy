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
package com.acidmanic.release.fileeditors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class JsonEditorTest {

    private String jsonContent = "{\n"
            + "  \"_from\": \"fs\",\n"
            + "  \"_id\": \"fs@0.0.1-security\",\n"
            + "  \"_inBundle\": false,\n"
            + "  \"_integrity\": \"sha1-invTcYa23d84E/I4WLV+yq9eQdQ=\",\n"
            + "  \"_location\": \"/fs\",\n"
            + "  \"_phantomChildren\": {},\n"
            + "  \"_requested\": {\n"
            + "    \"type\": \"tag\",\n"
            + "    \"registry\": true,\n"
            + "    \"raw\": \"fs\",\n"
            + "    \"name\": \"fs\",\n"
            + "    \"escapedName\": \"fs\",\n"
            + "    \"rawSpec\": \"\",\n"
            + "    \"saveSpec\": null,\n"
            + "    \"fetchSpec\": \"latest\"\n"
            + "  },\n"
            + "  \"_requiredBy\": [\n"
            + "    \"#USER\",\n"
            + "    \"/\"\n"
            + "  ],\n"
            + "  \"_resolved\": \"https://registry.npmjs.org/fs/-/fs-0.0.1-security.tgz\",\n"
            + "  \"_shasum\": \"8a7bd37186b6dddf3813f23858b57ecaaf5e41d4\",\n"
            + "  \"_spec\": \"fs\",\n"
            + "  \"_where\": \"/home/diego/Projects/Learning/Node\",\n"
            + "  \"author\": \"\",\n"
            + "  \"bugs\": {\n"
            + "    \"url\": \"https://github.com/npm/security-holder/issues\"\n"
            + "  },\n"
            + "  \"bundleDependencies\": false,\n"
            + "  \"deprecated\": false,\n"
            + "  \"description\": \"This package name is not currently in use, but was formerly occupied by another package. To avoid malicious use, npm is hanging on to the package name, but loosely, and we'll probably give it to you if you want it.\",\n"
            + "  \"homepage\": \"https://github.com/npm/security-holder#readme\",\n"
            + "  \"keywords\": [],\n"
            + "  \"license\": \"ISC\",\n"
            + "  \"main\": \"index.js\",\n"
            + "  \"name\": \"fs\",\n"
            + "  \"repository\": {\n"
            + "    \"type\": \"git\",\n"
            + "    \"url\": \"git+https://github.com/npm/security-holder.git\"\n"
            + "  },\n"
            + "  \"scripts\": {\n"
            + "    \"test\": \"echo \\\"Error: no test specified\\\" && exit 1\"\n"
            + "  },\n"
            + "  \"version\": \"0.0.1-security\"\n"
            + "}";

    private String jsonContentForSetTest = "{\n"
            + "  \"_from\": \"fs\",\n"
            + "  \"_id\": \"fs@0.0.1-security\",\n"
            + "  \"_inBundle\": false,\n"
            + "  \"_integrity\": \"sha1-invTcYa23d84E/I4WLV+yq9eQdQ=\",\n"
            + "  \"_location\": \"/fs\",\n"
            + "  \"_phantomChildren\": {},\n"
            + "  \"_requested\": {\n"
            + "    \"type\": \"tag\",\n"
            + "    \"registry\": true,\n"
            + "    \"raw\": \"fs\",\n"
            + "    \"name\": \"fs\",\n"
            + "    \"escapedName\": \"fs\",\n"
            + "    \"rawSpec\": \"\",\n"
            + "    \"saveSpec\": null,\n"
            + "    \"fetchSpec\": \"latest\"\n"
            + "  },\n"
            + "  \"_requiredBy\": [\n"
            + "    \"#USER\",\n"
            + "    \"/\"\n"
            + "  ],\n"
            + "  \"_resolved\": \"https://registry.npmjs.org/fs/-/fs-0.0.1-security.tgz\",\n"
            + "  \"_shasum\": \"8a7bd37186b6dddf3813f23858b57ecaaf5e41d4\",\n"
            + "  \"_spec\": \"fs\",\n"
            + "  \"_where\": \"/home/diego/Projects/Learning/Node\",\n"
            + "  \"author\": \"\",\n"
            + "  \"bugs\": {\n"
            + "    \"url\": \"https://github.com/npm/security-holder/issues\"\n"
            + "  },\n"
            + "  \"bundleDependencies\": false,\n"
            + "  \"deprecated\": false,\n"
            + "  \"description\": \"This package name is not currently in use, but was formerly occupied by another package. To avoid malicious use, npm is hanging on to the package name, but loosely, and we'll probably give it to you if you want it.\",\n"
            + "  \"homepage\": \"https://github.com/npm/security-holder#readme\",\n"
            + "  \"keywords\": [],\n"
            + "  \"license\": \"ISC\",\n"
            + "  \"main\": \"index.js\",\n"
            + "  \"name\": \"fs\",\n"
            + "  \"repository\": {\n"
            + "    \"type\": \"git\""
            + "  },\n"
            + "  \"scripts\": {\n"
            + "    \"test\": \"echo \\\"Error: no test specified\\\" && exit 1\"\n"
            + "  },\n"
            + "  \"version\": \"0.0.1-security\"\n"
            + "}";

    private String expectedAfterUpdateContent = "{\n"
            + "  \"_from\": \"fs\",\n"
            + "  \"_id\": \"fs@0.0.1-security\",\n"
            + "  \"_inBundle\": false,\n"
            + "  \"_integrity\": \"sha1-invTcYa23d84E/I4WLV+yq9eQdQ=\",\n"
            + "  \"_location\": \"/fs\",\n"
            + "  \"_phantomChildren\": {},\n"
            + "  \"_requested\": {\n"
            + "    \"type\": \"tag\",\n"
            + "    \"registry\": true,\n"
            + "    \"raw\": \"fs\",\n"
            + "    \"name\": \"fs\",\n"
            + "    \"escapedName\": \"fs\",\n"
            + "    \"rawSpec\": \"\",\n"
            + "    \"saveSpec\": null,\n"
            + "    \"fetchSpec\": \"latest\"\n"
            + "  },\n"
            + "  \"_requiredBy\": [\n"
            + "    \"#USER\",\n"
            + "    \"/\"\n"
            + "  ],\n"
            + "  \"_resolved\": \"https://registry.npmjs.org/fs/-/fs-0.0.1-security.tgz\",\n"
            + "  \"_shasum\": \"8a7bd37186b6dddf3813f23858b57ecaaf5e41d4\",\n"
            + "  \"_spec\": \"fs\",\n"
            + "  \"_where\": \"/home/diego/Projects/Learning/Node\",\n"
            + "  \"author\": \"\",\n"
            + "  \"bugs\": {\n"
            + "    \"url\": \"https://github.com/npm/security-holder/issues\"\n"
            + "  },\n"
            + "  \"bundleDependencies\": false,\n"
            + "  \"deprecated\": false,\n"
            + "  \"description\": \"This package name is not currently in use, but was formerly occupied by another package. To avoid malicious use, npm is hanging on to the package name, but loosely, and we'll probably give it to you if you want it.\",\n"
            + "  \"homepage\": \"https://github.com/npm/security-holder#readme\",\n"
            + "  \"keywords\": [],\n"
            + "  \"license\": \"ISC\",\n"
            + "  \"main\": \"index.js\",\n"
            + "  \"name\": \"fs\",\n"
            + "  \"repository\": {\n"
            + "    \"type\": \"notGit!\""
            + "  },\n"
            + "  \"scripts\": {\n"
            + "    \"test\": \"echo \\\"Error: no test specified\\\" && exit 1\"\n"
            + "  },\n"
            + "  \"version\": \"1.2.3-alpha\"\n"
            + "}";

    private final String[] versionAddress = new String[]{"version"};
    private final String oldVersion = "0.0.1-security";
    private final String newVersion = "1.2.3-alpha";
    
    private final  File jsonFile;
    private final  File writejsonFile;

    private final String[] repoTypeAddress = new String[]{"repository", "type"};
    private final String oldRepoType = "git";
    private final String newRepoType = "notGit!";

    public JsonEditorTest() throws IOException {
        this.jsonFile = makeJson(jsonContent, "package.json");
        this.writejsonFile = makeJson(jsonContentForSetTest, "package-write.json");
    }

    private File makeJson(String content, String path) throws IOException {

        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        Files.write(file.toPath(), content.getBytes(), StandardOpenOption.CREATE);
        return file;
    }

    @Test
    public void shouldReadOldValuesFromFile() {
        System.out.println("------ shouldReadOldValueFromFile -------");
        JsonEditor instance = new JsonEditor(jsonFile);

        String actualVersion = instance.readValue(versionAddress);
        String actualRepoType = instance.readValue(repoTypeAddress);
        assertEquals(oldVersion, actualVersion);
        assertEquals(oldRepoType, actualRepoType);
    }

    @Test
    public void shouldOnlyAndOnlyReplaceOldValues() throws IOException {
        System.out.println("------ shouldOnlyAndOnlyChangeRepoType -------");
        JsonEditor instance = new JsonEditor(writejsonFile);

        instance.setValue(versionAddress, newVersion);
        instance.setValue(repoTypeAddress, newRepoType);

        String actual = new String(Files.readAllBytes(writejsonFile.toPath()));

        actual = clearJsonWhite(actual);
        String expected = clearJsonWhite(expectedAfterUpdateContent);

        assertEquals(expected, actual);

    }

    private String clearJsonWhite(String value) {
        String ret = value;
        String[] seps = {"{", "}", "[", "]", ",", ":"};
        for (String sep : seps) {
            ret = ret.replaceAll("\\" + sep + "\\s+", sep);
            ret = ret.replaceAll("\\s+\\" + sep, sep);
        }
        return ret;
    }

}
