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
package com.acidmanic.release.versions.standard;

import java.util.HashMap;
import java.util.List;

/**
 * This class is model to represent properties of a section in a version
 * standard.
 *
 * @author Acidmanic
 */
public class VersionSection {

    public static final int SECTION_SEPARATOR_DOT = 0;
    public static final int SECTION_SEPARATOR_DASH = 1;

    public static final String RESET_BY_PREDECESSORS = "predecessors";
    public static final String RESET_BY_PREVIOUS = "previous";
    public static final String RESET_BY_ANY = "any";
    public static final String RESET_BY_NONE = "none";

    private boolean mandatory = true;
    private String tagPrefix = "";
    private String tagPostfix = "";
    private HashMap<Integer, String> namedValues;
    private int defaultValue = 0;
    private boolean defaultValueHidden = false;
    private String sectionName;
    private int separator = SECTION_SEPARATOR_DOT;
    private String reseters = RESET_BY_PREDECESSORS;
    private long globalWeightOrder = 3;

    public VersionSection() {
        this.namedValues = new HashMap<>();
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getTagPrefix() {
        return tagPrefix;
    }

    public void setTagPrefix(String tagPrefix) {
        this.tagPrefix = tagPrefix;
    }

    public String getTagPostfix() {
        return tagPostfix;
    }

    public void setTagPostfix(String tagPostfix) {
        this.tagPostfix = tagPostfix;
    }

    public HashMap<Integer, String> getNamedValues() {
        return namedValues;
    }

    public void setNamedValues(HashMap<Integer, String> namedValues) {
        this.namedValues = namedValues;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isDefaultValueHidden() {
        return defaultValueHidden;
    }

    public void setDefaultValueHidden(boolean hideSectionWhenValueIsDefault) {
        this.defaultValueHidden = hideSectionWhenValueIsDefault;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getSeparator() {
        return separator;
    }

    public void setSeparator(int separator) {
        this.separator = separator;
    }

    public long getGlobalWeightOrder() {
        return globalWeightOrder;
    }

    public void setGlobalWeightOrder(long globalWeightOrder) {
        this.globalWeightOrder = globalWeightOrder;
    }

    public String getReseters() {
        return reseters;
    }

    /**
     * sets the reseter string
     *
     * @param reseters list of names of sections that their incrementation will
     * reset this section. it also can take values: any-before,previous,
     * any,none. You can use constants VersionSection.RESET_BY_*
     */
    public void setReseters(String reseters) {
        this.reseters = reseters;
    }

}
