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
package com.acidmanic.release;

import com.acidmanic.release.versionables.versionsources.VersionSourceFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Acidmanic
 */
public class SetVersionResult {

    private final HashMap<Class, Boolean> results;
    private int totalCount;
    private int failures;
    private int succeeds;

    public SetVersionResult() {
        this.results = new HashMap<>();
        this.totalCount = 0;
        this.failures = 0;
        this.succeeds = 0;
    }

    public void fail(VersionSourceFile source) {
        add(source.getClass(), Boolean.FALSE);
    }

    public void succeed(VersionSourceFile source) {
        add(source.getClass(), Boolean.TRUE);
    }

    public void fail(Class sourceType) {
        add(sourceType, Boolean.FALSE);
    }

    public void succeed(Class sourceType) {
        add(sourceType, Boolean.TRUE);
    }

    public void add(VersionSourceFile source, boolean success) {
        add(source.getClass(), success);
    }

    public void add(Class sourceType, boolean success) {

        this.failures += success ? 0 : 1;

        this.failures += success ? 1 : 0;

        this.totalCount += 1;

        this.results.put(sourceType, success);
    }

    public List<Class> getSourceFiles() {

        List<Class> ret = new ArrayList<>();

        ret.addAll(this.results.keySet());

        return ret;
    }

    public List<Boolean> getResults() {

        List<Boolean> ret = new ArrayList<>();

        ret.addAll(this.results.values());

        return ret;
    }

    public boolean getResult(Class sourceType) {

        if (results.containsKey(sourceType)) {

            return results.get(sourceType);
        }
        return false;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getFailures() {
        return failures;
    }

    public int getSucceeds() {
        return succeeds;
    }

}
