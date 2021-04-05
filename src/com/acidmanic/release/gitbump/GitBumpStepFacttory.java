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

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Set;
import org.reflections.Reflections;

/**
 *
 * @author diego
 */
public class GitBumpStepFacttory {

    private HashMap<String, Class<GitBumpStep>> _steps;

    public GitBumpStepFacttory() {

        _steps = new HashMap<>();

        Reflections reflections = new Reflections();

        Set<Class<? extends GitBumpStep>> stepClasses
                = reflections.getSubTypesOf(GitBumpStep.class);

        for (Class stepType : stepClasses) {
            try {

                String key = stepType.getSimpleName().toLowerCase();

                _steps.put(key, stepType);
            } catch (Exception e) {
            }
        }

    }

    private GitBumpStep make(Class type, String[] arguments) {
        try {

            int numberOfArguments = arguments.length;

            Constructor[] constructors = type.getConstructors();

            for (Constructor constructor : constructors) {

                boolean stringtone = isStringTone(constructor, numberOfArguments);

                if (stringtone) {
                    Object[] values = objectize(arguments);

                    GitBumpStep ret = (GitBumpStep) constructor.newInstance(values);

                    return ret;
                }
            }

        } catch (Exception e) {
        }
        return new NullGitBumpStep();
    }

    public GitBumpStep make(String... args) {

        if (args != null && args.length > 0) {

            String methodName = args[0];

            String[] arguments = {};

            if (args.length > 1) {

                arguments = new String[args.length - 1];

                System.arraycopy(args, 1, arguments, 0, args.length - 1);
            }

            GitBumpStep step = makeStep(methodName, arguments);

            return step;
        }
        return new NullGitBumpStep();
    }

    private GitBumpStep makeStep(String methodName, String[] arguments) {

        String key = methodName.toLowerCase();

        if (_steps.containsKey(key)) {

            Class type = _steps.get(key);

            GitBumpStep ret = make(type, arguments);
            
            return ret;
        }

        return new NullGitBumpStep();
    }

    private boolean isStringTone(Constructor constructor, int numberOfArguments) {

        Parameter[] parameters = constructor.getParameters();

        if (parameters.length == numberOfArguments || constructor.isVarArgs()) {

            for (Parameter parameter : parameters) {

                if (!parameter.getType().equals(String.class)) {

                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private Object[] objectize(String[] arguments) {

        int length = arguments.length;

        Object[] ret = new Object[length];

        System.arraycopy(arguments, 0, ret, 0, length);

        return ret;
    }
}
