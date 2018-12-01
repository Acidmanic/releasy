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

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class XmlInPlaceEditor {

    public String getTagContent(String[] address, String content) {
        if (address == null || address.length == 0) {
            return content;
        }
        TagLocation location = getLocationOf(address, content);
        if (location == null) {
            return null;
        }
        return content.substring(location.getContent().getBeginIndex(),
                location.getContent().getEndIndex());

    }

    public TagLocation getLocationOf(String[] address, String content) {
        XmlTagFinder finder = new XmlTagFinder();
        TagLocation location = finder.findOutmost(address[0], content);
        for (int i = 1; i < address.length; i++) {
            if (location == null) {
                break;
            } else {
                location = finder.find(address[i], content, location);
            }
        }
        return location;
    }

    public String setTagContent(String[] address, String content, String newValue) {
        if (address == null || address.length == 0) {
            return content;
        }
        TagLocation location = getLocationOf(address, content);
        if (location != null) {
            String newContent = content.substring(0, location.getStartTag().getEndIndex());
            newContent += newValue;
            newContent += content.substring(location.getEndTag().getBeginIndex(),
                    content.length());
            return newContent;
        }
        return content;
    }
}
