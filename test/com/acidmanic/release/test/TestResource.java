/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.test;

import com.acidmanic.io.file.FileSystemHelper;
import com.acidmanic.io.file.TempFile;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author diego
 */
public class TestResource {
    
    
    public void putOutContent(String zippedContent,File directory){
        
        if(directory.exists()){
            new FileSystemHelper().deleteDirectory(directory.getAbsolutePath());
        }
        
        directory.mkdirs();
        
        String tempName = UUID.randomUUID().toString();
        
        File temp = directory.toPath().resolve(tempName).toFile();
        
        putOutFile(zippedContent, temp);
        
        extractFolder(temp, directory);
        
        temp.delete();
    }
    

    public void putOutFile(String resource, File destination) {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(resource)) {
            Files.copy(is, destination.toPath());
        } catch (IOException e) {
            // An error occurred copying the resource
        }
    }

    private void extractFolder(File zipFile, File extractFolder) {
        try {
            int BUFFER = 2048;

            ZipFile zip = new ZipFile(zipFile);

            extractFolder.mkdirs();
            
            Enumeration zipFileEntries = zip.entries();

            // Process each entry
            while (zipFileEntries.hasMoreElements()) {
                // grab a zip file entry
                ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
                String currentEntry = entry.getName();

                File destFile = extractFolder.toPath().resolve(currentEntry).toFile();
                //destFile = new File(newPath, destFile.getName());
                File destinationParent = destFile.getParentFile();

                // create the parent directory structure if needed
                destinationParent.mkdirs();

                if (!entry.isDirectory()) {
                    BufferedInputStream is = new BufferedInputStream(zip
                            .getInputStream(entry));
                    int currentByte;
                    // establish buffer for writing file
                    byte data[] = new byte[BUFFER];

                    // write the current file to disk
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest = new BufferedOutputStream(fos,
                            BUFFER);

                    // read and write until last byte is encountered
                    while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }

            }
        } catch (Exception e) {
            log("ERROR: " + e.getMessage());
        }

    }

    private void log(String string) {
        System.out.println(string);
    }

}
