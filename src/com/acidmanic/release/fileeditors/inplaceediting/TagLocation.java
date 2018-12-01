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
public class TagLocation {

    private SubString startTag;
    private SubString endTag;
    private SubString content;

    public TagLocation() {
        this.startTag = new SubString();
        this.endTag = new SubString();
        this.content = new SubString();

    }

    public TagLocation(SubString startTag, SubString endTag) {
        this.startTag = startTag;
        this.endTag = endTag;
        calculateContentTag();
    }

    private void calculateContentTag() {
        int s = this.startTag.getEndIndex();
        int e = this.endTag.getBeginIndex();
        if (s < e) {
            this.content = new SubString(s, e);
        } else {
            this.content = new SubString(s, s);
        }
    }

    public SubString getStartTag() {
        return startTag;
    }

    public void setStartTag(SubString startTag) {
        this.startTag = startTag;
        calculateContentTag();
    }

    public SubString getEndTag() {
        return endTag;
    }

    public void setEndTag(SubString endTag) {
        this.endTag = endTag;
        calculateContentTag();
    }

    public SubString getContent() {
        return content;
    }
    
    

}
