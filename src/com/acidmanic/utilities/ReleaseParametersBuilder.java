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
package com.acidmanic.utilities;

import com.acidmanic.release.models.ReleaseParameters;
import com.acidmanic.release.versionables.Versionable;
import com.acidmanic.release.versions.Change;
import com.acidmanic.release.versions.ReleaseTypes;
import com.acidmanic.release.versions.Version;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class ReleaseParametersBuilder {

    private final ReleaseParameters result;

    public ReleaseParametersBuilder() {
        this.result = new ReleaseParameters(new ArrayList<>(),
                Versionable.NULL, Version.NULL, ReleaseTypes.NIGHTLY,
                new Change(false, false, false)
        );
    }

    public ReleaseParametersBuilder versionables(List<Versionable> versionables) {
        this.result.setVersionables(versionables);
        return this;
    }

    public ReleaseParametersBuilder type(int releaseType) {
        this.result.setReleaseType(releaseType);
        return this;
    }

    public ReleaseParametersBuilder changes(Change changes) {
        this.result.setChanges(changes);
        return this;
    }

    public ReleaseParametersBuilder releaser(Versionable releaser) {
        this.result.setReleaser(releaser);
        return this;
    }

    public ReleaseParametersBuilder version(Version version) {
        this.result.setVersion(version);
        return this;
    }

    public ReleaseParameters build() {
        return this.result;
    }

}
