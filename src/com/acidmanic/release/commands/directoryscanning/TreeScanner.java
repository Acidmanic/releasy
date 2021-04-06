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

import com.acidmanic.release.utilities.DirectoryHelper;
import java.io.File;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Acidmanic
 */
public class TreeScanner implements DirectoryScanner{

    @Override
    public void scan(File directory, Function<File, Boolean> validator, Consumer<File> scanner) {
        
        new DirectoryHelper().scanTreeFiles(directory, validator, scanner);
    }
    
}
