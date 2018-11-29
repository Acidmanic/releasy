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
package com.acidmanic.parse;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class QuotationParser {

    public boolean isWrappedIn(String value, String tag) {
        return (value.startsWith(tag) && value.endsWith(tag));
    }

    public boolean isQuotedValues(String part) {
        return isWrappedIn(part, "'") || isWrappedIn(part, "\"");
    }

    public String unQoute(String qoutedValue) {
        return qoutedValue.substring(1, qoutedValue.length() - 1);
    }

    public String escapeAndQoute(String message, char c) {
        return c + escape(message, new char[]{c}) + c;
    }

    public boolean isEscape(char c, char[] escapeChars) {
        for (char es : escapeChars) {
            if (es == c) {
                return true;
            }
        }
        return false;
    }

    public String escape(String message, char[] escapeChars) {
        char[] chars = message.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (isEscape(c, escapeChars)) {
                sb.append("\\");
            }
            sb.append(c);
        }
        return sb.toString();
    }

}
