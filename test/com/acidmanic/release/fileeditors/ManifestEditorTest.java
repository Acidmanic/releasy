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
public class ManifestEditorTest {

    private File manifestFile;
    private final String KEY1 = "Implementation-Version";
    private final String VALUE1 = "vWhatever";
    private final String KEY2 = "Manifest-Version";
    private final String VALUE2 = "1.0";
    private final String MANIFEST_CONTENT = KEY1 + ": " + VALUE1
            + "\n" + KEY2 + ": " + VALUE2;
    private final String MANIFEST_ONELINE = KEY1 + ": " + VALUE1;

    public ManifestEditorTest() {
        manifestFile = new File(".").toPath().resolve("manifest.mf").toFile();
    }

    private void makeManifestFile() throws Exception {

        makeManifestFile(MANIFEST_CONTENT);
    }

    private void makeManifestFile(String content) throws Exception {

        if (manifestFile.exists()) {
            Files.delete(manifestFile.toPath());
        }

        Files.write(manifestFile.toPath(), content.getBytes(), StandardOpenOption.CREATE);
    }

    @Test
    public void shouldLoadBothKeyValues() throws Exception {

        System.out.println("---- shouldLoadBothKeyValues ----");

        makeManifestFile();

        ManifestEditor instance = new ManifestEditor();

        instance.load(manifestFile);

        assertEquals(VALUE1, instance.get(KEY1));

        assertEquals(VALUE2, instance.get(KEY2));
    }

    @Test
    public void shouldAddKeyValuesToFile() throws Exception {
        System.out.println("---- shouldLoadBothKeyValues ----");

        ManifestEditor instance = new ManifestEditor();

        if (manifestFile.exists()) {
            manifestFile.delete();
        }

        instance.set(KEY1, VALUE1);

        instance.save(manifestFile);

        String expected = KEY1 + ":" + VALUE1;

        String actual = readManifestFile();

        actual = actual.replaceAll("\\s", "");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldAddNewKeyKeepingOldOnes() throws Exception {
        System.out.println("---- shouldLoadBothKeyValues ----");

        ManifestEditor instance = new ManifestEditor();

        makeManifestFile(MANIFEST_ONELINE);

        instance.load(manifestFile);
        
        instance.set(KEY2, VALUE2);

        instance.save(manifestFile);

        String result = readManifestFile();
        
        assertTrue(result.contains(KEY1+":"));
        assertTrue(result.contains(VALUE1));
        
        assertTrue(result.contains(KEY2+":"));
        assertTrue(result.contains(VALUE2));
    }

    private String readManifestFile() {
        try {
            return new String(Files.readAllBytes(manifestFile.toPath()));
        } catch (Exception e) {
        }
        return "";
    }

}
