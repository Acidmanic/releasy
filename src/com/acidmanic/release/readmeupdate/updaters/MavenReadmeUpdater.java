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

import com.acidmanic.parse.indexbased.IndexBasedParser;
import com.acidmanic.parse.indexbased.SubString;
import com.acidmanic.parse.indexbased.TagLocation;
import com.acidmanic.release.fileeditors.inplaceediting.XmlInPlaceEditor;
import com.acidmanic.release.fileeditors.inplaceediting.XmlTagFinder;
import com.acidmanic.release.utilities.MavenPomVersionAdapter;
import com.acidmanic.release.versions.Version;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class MavenReadmeUpdater extends MavenInfoProvider implements ReadMeUpdater {

    private static final String[] TAG_ARTIFACT_ADDRESS = {"artifactId"};
    private static final String[] TAG_GROUP_ADDRESS = {"groupId"};

    private static final String MAVEN_DEPENDENCY = "dependency";
    private static final String TAG_VERSION = "version";

    @Override
    public String process(String readme, Version version, int releaseType) {
        // get groupId and artifactId from maven pom file
        provideMavenIinfo();

        if (isMaven) {
            // get all dependency tags in readmeFile
            List<TagLocation> tags = new XmlTagFinder().findAll(readme, MAVEN_DEPENDENCY);
            // filter out wrong tags based on groupId and artifactId
            tags = filterUnrelateds(readme, tags);
            // set version inside each tag
            List<SubString> versions = getVersionSubs(readme, tags);

            IndexBasedParser parser = new IndexBasedParser();

            String mavenVersion = new MavenPomVersionAdapter().versionToPomFileVersion(version, releaseType);

            readme = parser.replaceAll(readme, versions, mavenVersion);
        }
        return readme;
    }


    private List<TagLocation> filterUnrelateds(String content, List<TagLocation> tags) {

        List<TagLocation> ret = new ArrayList<>();

        XmlInPlaceEditor editor = new XmlInPlaceEditor();

        for (TagLocation tag : tags) {

            String artifact = editor.getTagContent(TAG_ARTIFACT_ADDRESS, content);
            String group = editor.getTagContent(TAG_GROUP_ADDRESS, content);

            if (equalsNotNull(artifact, this.artifactId)
                    && equalsNotNull(group, this.groupId)) {
                ret.add(tag);
            }
        }
        return ret;
    }

    private boolean equalsNotNull(String value1, String value2) {
        if (value1 == null || value2 == null) {
            return false;
        }
        return value1.compareTo(value2) == 0;
    }

    private List<SubString> getVersionSubs(String readme, List<TagLocation> tags) {

        List<SubString> ret = new ArrayList<>();

        XmlTagFinder finder = new XmlTagFinder();

        for (TagLocation tag : tags) {

            TagLocation versionTag = finder.find(TAG_VERSION, readme, tag);

            if (versionTag != null) {
                ret.add(versionTag.getContent());
            }

        }

        return ret;
    }

}
