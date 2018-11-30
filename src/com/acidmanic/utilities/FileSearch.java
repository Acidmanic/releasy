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
package com.acidmanic.utilities;

import com.acidmanic.parse.stringcomparison.StringComparisionFactory;
import com.acidmanic.parse.stringcomparison.StringComparison;
import java.io.File;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class FileSearch {

    public File search(File directory, String forFile, int stringComparision) {

        String fileNameLower = forFile.toLowerCase();

        StringComparison comparison = new StringComparisionFactory().make(stringComparision);

        File[] files = directory.listFiles((File dir, String name) -> comparison.areEqual(name, fileNameLower));

        if (files.length > 0) {
            return files[0];
        }

        return null;
    }

    public File search(File directory, String forFile) {
        return search(directory, forFile, StringComparison.COMPARE_CASE_SENSITIVE);
    }

}
