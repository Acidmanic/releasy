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
package com.acidmanic.release.commands.directoryscanning;

import com.acidmanic.release.directoryscanning.DirectoryScannerBundle;
import com.acidmanic.utilities.Final;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Acidmanic
 */
public class DirectoryScannerBundleTest {

    public DirectoryScannerBundleTest() {
    }

    @Test
    public void shouldFindCreatedFileOnCurrentDirectory() throws IOException {

        System.out.println("shouldFindCreatedFileOnCurrentDirectory");

        Final<Boolean> found = new Final(false);

        DirectoryScannerBundle instance = new DirectoryScannerBundle();

        instance.addCurrentDirectory(new File("."));

        File searchingFile = new File("searchingFile");

        if (!searchingFile.exists()) {

            searchingFile.createNewFile();
        }

        instance.scan(f -> true, f -> {
            System.out.println(f.toString());
            if (fileEqual(f, searchingFile)) {
                found.set(Boolean.TRUE);
            }
        });

        searchingFile.delete();
        
        assertTrue(found.get());
    }
    
    @Test
    public void shouldFindFileInSubDirectory() throws IOException {

        System.out.println("shouldFindFileInSubDirectory");

        Final<Boolean> found = new Final(false);

        DirectoryScannerBundle instance = new DirectoryScannerBundle();

        instance.addTree(new File("."));

        
        File subDirectory = new File(".").toPath().resolve("sub-dir")
                .resolve("subber-dir").toFile();
        
        subDirectory.mkdirs();

        File searchingFile = subDirectory.toPath().resolve("SEARCHIN-2").toFile();
        
        if (!searchingFile.exists()) {

            searchingFile.createNewFile();
        }

        instance.scan(f -> true, f -> {
            System.out.println(f.toString());
            if (fileEqual(f, searchingFile)) {
                found.set(Boolean.TRUE);
            }
        });

        searchingFile.delete();
        
        assertTrue(found.get());
    }

    private boolean fileEqual(File f1, File f2) {
        String file1 = f1.toPath().toAbsolutePath().normalize().toString();
        
        String file2 = f2.toPath().toAbsolutePath().normalize().toString();
        
        return file1.compareTo(file2)==0;
    }

}
