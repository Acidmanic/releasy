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
package com.acidmanic.release.versions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Acidmanic
 */
public class VersionParser {

    private final VersionStandard standard;
    private final long[] weights;

    public VersionParser(VersionStandard standard) {
        this.standard = standard;
        this.weights = calculateWeights(standard);
    }

    private String getVersionString(VersionModel version, boolean asTag) {

        StringBuilder sb = new StringBuilder();

        int[] values = version.toArray();

        for (int i = 0; i < values.length; i++) {

            VersionSection section = standard.getSections().get(i);

            int value = values[i];

            if (mustPresent(section, value)) {
                if (i > 0) {
                    String seprator = sepratorString(section.getSeparator());

                    sb.append(seprator);
                }
                if (asTag) {
                    sb.append(section.getTagPrefix());
                }
                String sectionString = getSectionAsString(value, section);

                sb.append(sectionString);

                if (asTag) {
                    sb.append(section.getTagPostfix());
                }
            }
        }
        return sb.toString();
    }

    public String getVersionString(VersionModel version) {

        return getVersionString(version, false);
    }

    public String getTagString(VersionModel version) {

        return getVersionString(version, true);
    }

    public VersionModel parse(String versionString) throws Exception {
        return parse(versionString, false);
    }

    public VersionModel parseTag(String versionString) throws Exception {
        return parse(versionString, true);
    }

    private ProgressResult progressString(int index,
            String searchString,
            String versionString) throws ParsingException {
                
        char[] searchChars = searchString.toCharArray();
        
        char[] versionChars = versionString.toCharArray();
        
        ProgressResult ret = new ProgressResult();
        
        for(int grow=0;grow<searchChars.length;grow++){
            
            int versionIndex = index + grow;
            
            if(versionIndex>=versionChars.length){
                throw new ParsingException();
            }
            if(versionChars[versionIndex]!=searchChars[grow]){
                throw new ParsingException();
            }
            ret.newIndex = versionIndex +1;
        }
        return ret;
    }

    private String sepratorString(int separator) {
        if (separator == VersionSection.SECTION_SEPARATOR_DASH) {
            return "-";
        }
        return ".";
    }

    private ProgressResult progressStrings(int index,
            Collection<String> values,
            String versionString) throws ParsingException {
        List<String> searchValues = new ArrayList<>();
        
        searchValues.addAll(values);
        
        searchValues.sort((s1,s2) -> s2.length() -s1.length());
        
        for(String search : searchValues){
            
            try {
                ProgressResult result = progressString(index, search, versionString);
                
                return result;
            } catch (Exception e) {
            }
        }
        throw new ParsingException();
    }

    private int getValueOf(String value, HashMap<Integer, String> namedValues) throws ParsingException {
        
        String lowerValue = value.toLowerCase();
        
        for(int key : namedValues.keySet()){
            
            String currentValue = namedValues.get(key).toLowerCase();
            
            if(currentValue.compareTo(lowerValue)==0){
                return key;
            }
        }
        throw new ParsingException();
    }

    private ProgressResult progressInteger(int index, String versionString) throws ParsingException {

        char[] chars = versionString.toCharArray();
        ProgressResult ret = new ProgressResult();

        if (index < chars.length) {

            char currentChar = chars[index];

            ret.newIndex = index;
            ret.value = "";
            boolean available = false;

            while (Character.isDigit(currentChar) && available) {

                ret.value += currentChar;

                index += 1;

                ret.newIndex = index;

                if (index < chars.length) {
                    currentChar = chars[index];
                } else {
                    available = false;
                }
            }
        }
        if (!(ret.newIndex > index)) {
            throw new ParsingException();
        }
        return ret;
    }

    private long[] calculateWeights(VersionStandard standard) {

        int count = standard.getSections().size();
        
        long[] ret = new long[count];

        long weight = 1;

        for (int i = count-1;i>=0;i--){
            
            long current = 10^standard.getSections().get(i).getGlobalWeightOrder();
            
            weight*=current;
            
            ret[i] = weight;
        }
        
        return ret;
    }

    private class ProgressResult {

        public int newIndex;
        public String value;
    }

    private class ParsingException extends Exception {

        public ParsingException() {
            super("Parsing Exception");
        }
    }

    private VersionModel parse(String versionString, boolean asTag) throws Exception {

        int index = 0;

        int sectionIndex = 0;

        int sectionCount = standard.getSections().size();

        VersionModel ret = new VersionModel(standard.getSections().size());

        while (index < versionString.length() && sectionIndex < sectionCount) {

            ProgressResult result;

            VersionSection section = standard.getSections().get(sectionIndex);

            if (sectionIndex > 0) {

                int seprator = section.getSeparator();

                String sepratorString = sepratorString(seprator);

                result = progressString(index, sepratorString, versionString);

                index = result.newIndex;
            }
            if (asTag) {

                result = progressString(index, section.getTagPrefix(), versionString);

                index = result.newIndex;
            }
            int value;

            if (section.getNamedValues().size() > 0) {

                result = progressStrings(index, section.getNamedValues().values(), versionString);

                value = getValueOf(result.value, section.getNamedValues());

                index = result.newIndex;
            } else {

                result = progressInteger(index, versionString);

                value = Integer.parseInt(result.value);

                index = result.newIndex;
            }
            ret.setValue(sectionIndex, value);

            long weight = this.weights[sectionIndex];

            ret.setWeight(sectionIndex, weight);

            if (asTag) {

                result = progressString(index, section.getTagPostfix(), versionString);

                index = result.newIndex;
            }

            sectionIndex += 1;
        }
        for (int i = sectionIndex; i < sectionCount; i++) {

            VersionSection section = standard.getSections().get(i);

            int def = section.getDefaultValue();

            ret.setValue(i, def);

            long weight = this.weights[i];

            ret.setWeight(i, weight);
        }
        return ret;
    }

    public String getTemplate(boolean asTag) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < standard.getSections().size(); i++) {

            VersionSection section = standard.getSections().get(i);

            if (i > 0) {
                sb.append(section.getSeparator());
            }
            if (asTag) {
                sb.append(section.getTagPrefix());
            }
            sb.append("<")
                    .append(section.getSectionName())
                    .append(">");
            if (asTag) {
                sb.append(section.getTagPostfix());
            }
        }
        return sb.toString();
    }

    private String getSectionAsString(int value, VersionSection section) {

        if (section.getNamedValues().containsKey(value)) {
            return section.getNamedValues().get(value);
        }

        return Integer.toString(value);
    }

    private boolean mustPresent(VersionSection section, int value) {
        return !section.isDefaultValueHidden()
                || value != section.getDefaultValue();
    }

}
