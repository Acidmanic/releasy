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
package release.inapptests;

import com.acidmanic.installation.tasks.InstallApplicationContent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class InstallApplicationContentTest extends InstallationTaskTestBase {

    @Test
    public void shouldCopyLibsDirectoryWith4JarsInIt() {
        InstallApplicationContent task = new InstallApplicationContent();
        List<String> contents = new ArrayList<>();
        contents.add("lib");

        boolean success = runExecuteMethod(task, contents);

        assertEquals(true, success);
        List<String> result = task.getResult();
        assertTrue(result.size()>=4);
        for (String path : result) {
            File file = new File(path);
            assertEquals(true, file.exists());
            System.out.println(file.getName() + " has been copied correctly.");
        }
    }
}
