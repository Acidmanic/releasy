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

import com.acidmanic.io.file.FileIOHelper;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class AssemblyInfoEditorTest {

    private String content = "// by using the '*' as shown below:\n"
            + "// [assembly: AssemblyVersion(\"1.0.*\")]\n"
            + "[assembly: AssemblyVersion(\"1.4.7.2\")]\n"
            + "[assembly: AssemblyFileVersion(\"4.6.6.6\")]";

    private String versionToSet = "1.0.3.7";

    private String replaced = "// by using the '*' as shown below:\n"
            + "// [assembly: AssemblyVersion(\"1.0.*\")]\n"
            + "[assembly: AssemblyVersion(\"" + versionToSet + "\")]\n"
            + "[assembly: AssemblyFileVersion(\"" + versionToSet + "\")]";

    private File assFile;

    public AssemblyInfoEditorTest() {
        assFile = new File(".").toPath().resolve("assembly.cs").toFile();

    }

    private void resetAssemblyFile() {
        if (assFile.exists()) {
            assFile.delete();
        }
        new FileIOHelper().tryWriteAll(assFile, content);
    }

    @Test
    public void shouldBeAbleToReadTwoFields() {
        System.out.println("------------shouldBeAbleToReadTwoFields--------------");
        resetAssemblyFile();
        AssemblyInfoEditor sut = new AssemblyInfoEditor();
        String expected = "1.4.7.2";
        String expected2 = "4.6.6.6";
        assertEquals(expected, sut.readValue(assFile, "AssemblyVersion"));
        assertEquals(expected2, sut.readValue(assFile, "AssemblyFileVersion"));
    }

    @Test
    public void shouldSetBothValuesToNewValue() {
        System.out.println("------------shouldSetBothValuesToNewValue--------------");
        resetAssemblyFile();
        AssemblyInfoEditor sut = new AssemblyInfoEditor();
        sut.writeValue(assFile, "AssemblyVersion", versionToSet);
        sut.writeValue(assFile, "AssemblyFileVersion", versionToSet);
        String actual = new FileIOHelper().tryReadAllText(assFile);
        assertEquals(replaced, actual);
    }

}
