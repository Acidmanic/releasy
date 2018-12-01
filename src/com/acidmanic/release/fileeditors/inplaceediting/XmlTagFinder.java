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
package com.acidmanic.release.fileeditors.inplaceediting;

import com.acidmanic.parse.indexbased.SubString;
import com.acidmanic.parse.indexbased.TagLocation;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class XmlTagFinder {

    public TagLocation findOutmost(String name, String content) {
        return find(name, content, new SubString(0, content.length()));
    }

    public TagLocation find(String name, String content, SubString clip) {
        int st = content.indexOf("<" + name, clip.getBeginIndex() );
        if (st != -1) {
            String ndTag = "</" + name;
            int nd = content.indexOf(ndTag, st);
            if (nd != -1) {
                int hend = content.indexOf(">", st);
                int tend = content.indexOf(">", nd);
                SubString head = new SubString(st, hend + 1);
                SubString tail = new SubString(nd, tend + 1);
                return new TagLocation(head, tail);
            }
        }
        return null;
    }

    public TagLocation find(String name, String content, TagLocation parent) {
        int st = content.indexOf("<" + name, parent.getContent().getBeginIndex() + 1);
        if (st != -1) {
            String ndTag = "</" + name;
            int nd = content.indexOf(ndTag, st);
            if (nd != -1) {
                int hend = content.indexOf(">", st);
                int tend = content.indexOf(">", nd);
                SubString head = new SubString(st, hend + 1);
                SubString tail = new SubString(nd, tend + 1);
                return new TagLocation(head, tail);
            }
        }
        return null;
    }
}
