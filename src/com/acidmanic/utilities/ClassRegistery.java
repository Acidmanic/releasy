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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class ClassRegistery {

    private final ArrayList<Class> classes;

    /**
     * *
     * Singleton
     */
    protected ClassRegistery() {
        this.classes = new ArrayList<>();
    }

    private static ClassRegistery instance = null;

    public static synchronized ClassRegistery makeInstance() {
        if (instance == null) {
            instance = new ClassRegistery();
        }
        return instance;
    }

    public void add(Class type) {
        this.classes.add(type);
    }

    public <T> T makeInstance(Class type) throws Exception {
        return (T) type.newInstance();
    }

    public <T> T tryMakeInstance(Class type) {
        try {
            return makeInstance(type);
        } catch (Exception e) {
        }
        return null;
    }

    public <T> List<T> all(Class<? extends T> type) {
        ArrayList<T> ret = new ArrayList<>();
        for (Class t : this.classes) {
            try {
                Object o = t.newInstance();
                ret.add(type.cast(o));
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public List<Object> all() {
        return all(Object.class);
    }

}
