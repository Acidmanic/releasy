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
package com.acidmanic.release.readmeupdate.updaters;

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.release.fileeditors.inplaceediting.XmlInPlaceEditor;
import com.acidmanic.utilities.FileSearch;
import java.io.File;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class MavenInfoProvider {
    
    protected static final String[] PROJECT_ARTIFACT_ADDRESS = {"project", "artifactId"};
    protected static final String[] PROJECT_GROUP_ADDRESS = {"project", "groupId"};
    protected String artifactId;
    protected String groupId;
    protected boolean isMaven;

    protected void provideMavenIinfo() {
        File here = new File(".");
        File pomFile = new FileSearch().search(here, "pom.xml");
        if (pomFile != null && pomFile.exists()) {
            String mavenContent = new FileIOHelper().tryReadAllText(pomFile);
            if (mavenContent != null && mavenContent.length() > 0) {
                this.isMaven = true;
                XmlInPlaceEditor xmlEditor = new XmlInPlaceEditor();
                this.artifactId = xmlEditor.getTagContent(PROJECT_ARTIFACT_ADDRESS, mavenContent);
                this.groupId = xmlEditor.getTagContent(PROJECT_GROUP_ADDRESS, mavenContent);
            }
        }
    }
    
}
