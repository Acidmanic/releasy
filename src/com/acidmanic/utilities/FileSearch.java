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

import com.acidmanic.io.file.SimpleFileVisitor;
import com.acidmanic.parse.stringcomparison.StringComparisionFactory;
import com.acidmanic.parse.stringcomparison.StringComparison;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.StringContent;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class FileSearch {

    public interface ValidateName{
        boolean validate(String name);
    }
    
    public File search(File directory, String forFile, int stringComparision) {


        StringComparison comparison = new StringComparisionFactory().make(stringComparision);

        File[] files = directory.listFiles((File dir, String name) -> comparison.areEqual(name, forFile));

        if (files.length > 0) {
            return files[0];
        }

        return null;
    }

    public File search(File directory, String forFile) {
        return search(directory, forFile, StringComparison.COMPARE_CASE_SENSITIVE);
    }
    
    public List<File> searchTree(Path src, ValidateName validator)  {
        List<File> ret = new ArrayList<>();
        try {
            Files.walkFileTree(src, new SimpleFileVisitor() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    File f = file.toAbsolutePath().normalize().toFile();
                    if (validator.validate(f.getName())){
                        ret.add(f);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
        }
        return ret;
    }
    
    
    public List<File> searchTree(Path src, String name,int stringComparision){
        StringComparison comparison = new StringComparisionFactory().make(stringComparision);
        return searchTree(src, (String fileName) -> comparison.areEqual(fileName, name));
    }
    
    public List<File> searchTree(Path src, String name){
        StringComparison comparison = new StringComparisionFactory()
                .make(StringComparison.COMPARE_CASE_SENSITIVE);
        return searchTree(src, (String fileName) -> comparison.areEqual(fileName, name));
    }

}
