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
package com.acidmanic.release.configurabletools;

import com.acidmanic.utilities.TextFileUpdater;
import com.acidmanic.release.versions.SemanticVersion;
import com.acidmanic.release.versions.Version;
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
public class TextFileUpdaterTest {

    private String TAG = "REPLACING_TAG";
    
    
    private final Version version = new SemanticVersion(1, 2, 3, "latest-version");

    private final String rawContent = "This file contains two tags, "
            + " first tag is this: " + TAG
            + " whitch should be replaced. the second one is \\"
            + TAG + ", whitch is escaped "
            + "and should not be replaced. as a third (!) lets try this:"
            + TAG + ", which is contatinaed "
            + "in both sides but should be replaced.";
    
    private final String replacedContent = "This file contains two tags, "
            + " first tag is this: " + version.getVersionString()
            + " whitch should be replaced. the second one is "
            + TAG + ", whitch is escaped "
            + "and should not be replaced. as a third (!) lets try this:"
            + version.getVersionString() + ", which is contatinaed "
            + "in both sides but should be replaced.";

    private final File readmeFile = new File("ReadMe.md");
    
    public TextFileUpdaterTest() {
        
    }

    private void setupReadMeFile(){
        try {
            if(readmeFile.exists()){
                readmeFile.delete();
            }
            Files.write(readmeFile.toPath(), rawContent.getBytes(), StandardOpenOption.CREATE);
        } catch (Exception e) {
        }
    }
    
    @Test
    public void shouldReplaceFistAndThirdOccurencesOfTag() throws IOException {
        System.out.println("------- shouldReplaceFistAndThirdOccurencesOfTag ------- ");
        setupReadMeFile();
        TextFileUpdater instance = new TextFileUpdater(TAG);
        instance.updateFile(readmeFile, version.getVersionString());
        String actual = new String(Files.readAllBytes(readmeFile.toPath()));
        assertEquals(replacedContent, actual);
    }
    
    

}
