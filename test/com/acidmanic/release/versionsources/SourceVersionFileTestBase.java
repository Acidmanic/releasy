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
package com.acidmanic.release.versionsources;

import com.acidmanic.release.directoryscanning.DirectoryScanner;
import com.acidmanic.release.directoryscanning.DirectoryScannerBundle;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Acidmanic
 */
public abstract class SourceVersionFileTestBase {

    protected String THE_VERSION = "111.222.333";

    private File versionFile;

    public SourceVersionFileTestBase() {

    }

    @Test
    public void shouldDetectSourceFilePresence() {

        System.out.println("shouldDetectSourceFilePresence");

        VersionSourceFile sut = setupEnvironment();

        boolean actual = sut.isPresent();

        deleteSourceFile();

        assertTrue(actual);
    }

    @Test
    public void shouldDetectSourceFileAbsence() {

        System.out.println("shouldDetectSourceFileAbsence");
        
        VersionSourceFile sut = setupEnvironment();

        deleteSourceFile();

        boolean actual = sut.isPresent();

        assertTrue(actual);
    }

    @Test
    public void shouldSetVersionWithNoError() {

        System.out.println("shouldSetVersionWithNoError");

        VersionSourceFile sut = setupEnvironment();

        boolean actual = sut.setVersion(THE_VERSION);

        deleteSourceFile();

        assertTrue(actual);
    }

    @Test
    public void shouldReadVersionCorrectly() {

        System.out.println("shouldReadVersionCorrectly");

        VersionSourceFile sut = setupEnvironment();

        List<String> versions = sut.getVersions();

        deleteSourceFile();

        assertEquals(1, versions.size());

        assertEquals(THE_VERSION, versions.get(0));

    }

    @Test
    public void shouldSetAndReadNewVersion() {

        System.out.println("shouldSetAndReadNewVersion");

        VersionSourceFile sut = setupEnvironment();

        String newVersion = "39.23.78";

        sut.setVersion(newVersion);

        List<String> versions = sut.getVersions();

        deleteSourceFile();

        assertEquals(1, versions.size());

        assertEquals(newVersion, versions.get(0));

    }

    protected abstract File makeSourceFile(String versionString);

    protected void deleteSourceFile() {
        if (versionFile.exists()) {
            versionFile.delete();
        }
    }

    protected abstract VersionSourceFile createNew();

    private VersionSourceFile createSystemUnderTest() {

        VersionSourceFile sut = createNew();

        DirectoryScannerBundle scanners = createSingleBundle(this.versionFile);

        sut.setup(scanners);

        return sut;
    }

    private DirectoryScannerBundle createSingleBundle(File versionFile) {

        return new DirectoryScannerBundle().add(versionFile, new SingleFileScanner(versionFile));
    }

    private VersionSourceFile setupEnvironment() {
        
        this.versionFile = makeSourceFile(THE_VERSION);
        
        return createSystemUnderTest();
    }

    private class SingleFileScanner implements DirectoryScanner {

        private final File file;

        public SingleFileScanner(File file) {
            this.file = file;
        }

        @Override
        public void scan(File directory, Function<File, Boolean> validator, Consumer<File> scanner) {

            if (validator.apply(file)) {

                scanner.accept(file);
            }
        }

    }

}
