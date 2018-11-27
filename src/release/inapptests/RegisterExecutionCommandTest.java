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

import com.acidmanic.installation.models.Scription;
import com.acidmanic.installation.tasks.RegisterExecutionCommand;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class RegisterExecutionCommandTest {
    
    public RegisterExecutionCommandTest() {
    }

    @Test
    public void shouldReturnTrueSucceedingToAddExecutableScript() {
        RegisterExecutionCommand cmd = new RegisterExecutionCommand();
        boolean actual = cmd.execute(new Scription("echo Mani", "test-install-mani"));
        boolean expected = true;
        assertEquals(expected, actual);
    }
    
}
