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
package com.acidmanic.installation.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class Copier {
    
    public List<String> copyAny(File src,File dst){
        List<String> copiedList = new ArrayList<>();
        copyAny(src, dst, copiedList);
        return copiedList;
    }
    
    public List<String> copyDirectory(File src, File dst) {
        List<String> copiedList = new ArrayList<>();
        copyDirectory(src, dst, copiedList);
        return copiedList;
    }
    
    public List<String> copyFileToDirectory(File src, File dst) {
        List<String> copiedList = new ArrayList<>();
        copyFileToDirectory(src, dst, copiedList);
        return copiedList;
    }
    
    
    public  void copyAny(File src, File dst, List<String> copiedList) {
        if (src.isFile()) {
            if (dst.isDirectory()) {
                copyFileToDirectory(src, dst, copiedList);
            } else {
                copyFileToDirectory(src,
                        dst.toPath().getParent().normalize().toFile(), copiedList);
            }
        } else {
            copyDirectory(src, dst, copiedList);
        }
    }
    

    public void copyDirectory(File src, File dst, List<String> copiedList) {
        File dstDouble = dst.toPath().resolve(src.getName()).toFile();
        if (!dstDouble.exists()) {
            dstDouble.mkdirs();
        }
        File[] files = src.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                copyDirectory(file, dstDouble, copiedList);
            } else {
                copyFileToDirectory(file, dstDouble, copiedList);
            }
        }
        copiedList.add(dstDouble.getAbsolutePath());
    }

    public void copyFileToDirectory(File src, File dst, List<String> copiedList) {
        try {
            File dstFile = dst.toPath().resolve(src.getName()).toFile();
            Files.copy(src.toPath(), dstFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            copiedList.add(dstFile.getAbsolutePath());
        } catch (Exception e) {
        }
    }
}
