package org.jtwig.functions.impl;

import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.value.Undefined;

import java.math.BigDecimal;
import java.util.Map;

public class BooleanFunctions {
    @JtwigFunction("defined")
    public boolean isDefined(@Parameter Object value) {
        return value != Undefined.UNDEFINED;
    }

    @JtwigFunction("null")
    public boolean isNull(@Parameter Object input) {
        return input == null;
    }

    @JtwigFunction("even")
    public boolean even(@Parameter int number) {
        return number % 2 == 0;
    }

    @JtwigFunction("odd")
    public boolean odd(@Parameter int number) {
        return number % 2 == 1;
    }

    @JtwigFunction("iterable")
    public boolean iterable(@Parameter Object input) {
        return input instanceof Iterable
                || input.getClass().isArray()
                || input instanceof Map;
    }

    @JtwigFunction("empty")
    public boolean isEmpty(@Parameter Object input) {
        return isNull(input) ||
                !isDefined(input) ||
                isEmptyIterable(input) ||
                isEmptyMap(input) ||
                isZero(input);
    }

    private boolean isZero(Object input) {
        try {
            return new BigDecimal(input.toString())
                    .compareTo(BigDecimal.ZERO) == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isEmptyMap(Object input) {
        return input instanceof Map && ((Map) input).isEmpty();
    }

    private boolean isEmptyIterable(Object input) {
        return input instanceof Iterable && !((Iterable) input).iterator().hasNext();
    }
}
