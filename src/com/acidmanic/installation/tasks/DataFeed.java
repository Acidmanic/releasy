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

/**
 * This task will block data stream from previous task and feed its own data to
 * next task. this data is the data parameter provided through its constructor.
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 * @param <Tout> Type of feeding data.
 *
 */
public class DataFeed<Tout> extends InstallationTask<Void, Tout> {

    public DataFeed(Tout data) {
        this.result = data;
    }

    @Override
    protected boolean onWindows(Void input) {
        return true;
    }

    @Override
    protected boolean onUnix(Void input) {
        return true;
    }

}
