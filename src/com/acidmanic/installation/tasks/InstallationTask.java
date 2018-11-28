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
package com.acidmanic.installation.tasks;

import com.acidmanic.installation.environment.EnvironmentalInfo;
import com.acidmanic.installation.utils.Os;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 * @param <Tin>
 * @param <Tout>
 */
public abstract class InstallationTask<Tin, Tout> {

    protected Tout result;

    protected abstract boolean onWindows(Tin input);

    protected abstract boolean onUnix(Tin input);

    private EnvironmentalInfo environmentalInfo;

    protected boolean onMac(Tin input) {
        return onUnix(input);
    }

    protected boolean onLinux(Tin input) {
        return onUnix(input);
    }

    public InstallationTask() {
        initialize();
    }

    private void initialize() {
    }

    public boolean isIgnorable() {
        return false;
    }

    public boolean execute(Tin input) {
        this.result = null;
        System.out.println("Performing: " + this.getClass().getSimpleName());
        try {
            Os os = new Os();
            if (os.isLinux()) {
                return onLinux(input);
            }
            if (os.isMac()) {
                return onMac(input);
            }
            if (os.isWindows()) {
                return onWindows(input);
            }
        } catch (Exception e) {
        }
        return false;
    }

    public Tout getResult() {
        return result;
    }

    public EnvironmentalInfo getEnvironmentalInfo() {
        return environmentalInfo;
    }

    public void setEnvironmentalInfo(EnvironmentalInfo environmentalInfo) {
        this.environmentalInfo = environmentalInfo;
    }

}
