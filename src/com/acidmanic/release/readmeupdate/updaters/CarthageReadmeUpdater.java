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

import com.acidmanic.parse.QuotationParser;
import com.acidmanic.parse.indexbased.IndexBasedParser;
import com.acidmanic.parse.indexbased.SubString;
import com.acidmanic.release.versions.Version;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class CarthageReadmeUpdater implements ReadmeUpdater {

    private static final String DOMAIN_URI_REGEX = "[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static final String HTTP_REGEX = "^(https?)://" + DOMAIN_URI_REGEX;
    private static final String SSH_REGEX = "(\\w|\\d)+@(\\w|\\d)+(\\.(\\w|\\d)+)*:/?" + DOMAIN_URI_REGEX;
    private static final String CORDINATES_REGEX = "(\\w|\\d)+/(\\w|\\d)+";

    private class CarthageInfo {

        public String origin;
        public String address;
        public String versionOperators;
        public String version;
    }

    @Override
    public String process(String readme, Version version, int releaseType) {

        String[] lines = readme.split("\\n");
        StringBuilder sb = new StringBuilder();

        String sep = "";

        String sVer = version.getVersionString();
        for (String line : lines) {
            CarthageInfo cart = readAsCarthageLine(line);
            if (cart != null) {
                line = updateLine(line, sVer, cart);
            }
            sb.append(sep).append(line);
            sep = "\n";
        }

        return sb.toString();
    }

    private CarthageInfo readAsCarthageLine(String line) {
        line = line.trim();
        String[] segments = line.split("\\s");
        if (segments.length > 3) {
            if (isCartStart(segments[0])) {
                QuotationParser q = new QuotationParser();
                if (q.isQuotedValues(segments[1])) {
                    CarthageInfo ret = new CarthageInfo();
                    ret.origin = segments[0];
                    ret.address = q.unQoute(segments[1]);
                    ret.versionOperators = segments[2];
                    ret.version = segments[3];
                    if (valid(ret)) {
                        return ret;
                    }
                }
            }
        }
        return null;
    }

    private boolean isCartStart(String segment) {
        return "git".compareTo(segment) == 0
                || "github".compareTo(segment) == 0;
    }

    private String updateLine(String line, String version, CarthageInfo cart) {
        int st = line.lastIndexOf(cart.version);
        SubString sub = new SubString(st, st + cart.version.length());
        return new IndexBasedParser().replace(line, sub, version);
    }

    private boolean valid(CarthageInfo ret) {
        if (!validOperators(ret.versionOperators)) {
            return false;
        }

        if ("git".compareTo(ret.origin) == 0) {
            if (!isValigByRX(ret.address, HTTP_REGEX)
                    && !isValigByRX(ret.address, SSH_REGEX)) {
                return false;
            }
        } else if ("github".compareTo(ret.origin) == 0) {
            if (!isValigByRX(ret.address, CORDINATES_REGEX)) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean validOperators(String versionOperators) {
        if (versionOperators != null && versionOperators.length() == 2) {
            versionOperators = versionOperators.replaceAll("=", "");
            versionOperators = versionOperators.replaceAll(">", "");
            versionOperators = versionOperators.replaceAll("~", "");
            return versionOperators.length() == 0;
        }
        return false;
    }

    private boolean isValigByRX(String value, String reg) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return value.replaceAll(reg, "").trim().isEmpty();
    }

}
