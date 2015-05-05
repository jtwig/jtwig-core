package org.jtwig.functions.impl;

import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.value.Undefined;

import java.lang.reflect.Array;

public class ObjectFunctions {
    @JtwigFunction("default")
    public Object defaultFunction (@Parameter Object input, @Parameter Object defaultValue) {
        if (input == null || input.equals(Undefined.UNDEFINED))
            return defaultValue;
        else
            return input;
    }


    @JtwigFunction("length")
    public int length (@Parameter Object input) {
        if (input instanceof String)
            return ((String) input).length();
        else
            return 1;
    }


    @JtwigFunction("first")
    public Object first (@Parameter Object input) {
        if (input == null) return input;
        if (input.getClass().isArray()) {
            if (Array.getLength(input) == 0) return null;
            return Array.get(input, 0);
        } else return input;
    }

    @JtwigFunction("last")
    public Object last (@Parameter Object input) {
        if (input == null) return input;
        if (input.getClass().isArray()) {
            int length = Array.getLength(input);
            if (length == 0) return null;
            return Array.get(input, length - 1);
        } else return input;
    }
}
