package org.jtwig.property.method;

import java.io.Serializable;
import java.util.List;

import static java.util.Arrays.asList;

public class IsNullableType {
    public static final List<? extends Class<? extends Serializable>> NATIVE_TYPES = asList(Integer.TYPE, Float.TYPE, Double.TYPE, Long.TYPE, Boolean.TYPE, Character.TYPE);

    public boolean isNullable (Class type) {
        return !NATIVE_TYPES.contains(type);
    }
}
