/*
 * Copyright (C) 2020 diego
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
package com.acidmanic.release.gitbump;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author diego
 */
public class GitBumpBuilder {

    private final ArrayList<GitBumpStep> steps;

    public GitBumpBuilder() {

        this.steps = new ArrayList<>();

        Initialize step = new Initialize();

        this.steps.add(step);
    }

    public GitBumpBuilder checkOut(String branch) {

        GitBumpStep step = new Checkout(branch);

        this.steps.add(step);

        return this;
    }

    public GitBumpBuilder increment(String sectionName) {

        GitBumpStep step = new Increment(sectionName);

        this.steps.add(step);

        return this;

    }

    public GitBumpBuilder merge(String branch) {

        GitBumpStep step = new Merge(branch);

        this.steps.add(step);

        return this;
    }

    public GitBumpBuilder pull(String branch) {

        GitBumpStep step = new Pull(branch);

        this.steps.add(step);

        return this;
    }

    public GitBumpBuilder push(String branch) {

        GitBumpStep step = new Push(branch);

        this.steps.add(step);

        return this;
    }

    public List<GitBumpStep> build() {

        ArrayList<GitBumpStep> ret = new ArrayList<>();

        steps.forEach(s -> {
            id(s);
            ret.add(s);
        });

        return ret;
    }

    private void id(GitBumpStep step) {

        String uuid = UUID.randomUUID().toString();

        step.setId(uuid);
    }

}
