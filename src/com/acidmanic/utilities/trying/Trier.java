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
package com.acidmanic.utilities.trying;

import com.acidmanic.utilities.delegates.Action1;
import com.acidmanic.utilities.delegates.Action;
import com.acidmanic.utilities.delegates.UnsafeAction;
import com.acidmanic.utilities.delegates.UnsafeFunction;
import com.acidmanic.utilities.Result;

/**
 *
 * @author Acidmanic
 */
public class Trier {

    public <Tout> Tout tryFunction(UnsafeFunction<Tout> function, Tout defaultValue) {

        try {

            return function.invoke();

        } catch (Exception e) {
        }
        return defaultValue;
    }

    public <Tout> Result<Tout> tryFunction(UnsafeFunction<Tout> function) {

        try {

            Tout res = function.invoke();

            return Result.success(res);

        } catch (Exception e) {
            return Result.fail(e.getClass().getSimpleName() + " " + e.getMessage());
        }
    }

    public void tryAction(UnsafeAction action) {

        try {

            action.invoke();

        } catch (Exception e) {
        }
    }

    public void tryAction(UnsafeAction action, Action onFailure) {

        try {

            action.invoke();

        } catch (Exception e) {

            onFailure.invoke();
        }
    }

    public void tryAction(UnsafeAction action, Action1<Exception> onFailure) {

        try {

            action.invoke();

        } catch (Exception e) {

            onFailure.invoke(e);
        }
    }

    public boolean tryFunction(UnsafeAction action) {

        try {

            action.invoke();

            return true;
        } catch (Exception e) {

        }
        return false;
    }

}
