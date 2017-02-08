package org.jtwig.property.strategy.method.argument;

import java.io.Serializable;
import java.util.List;

import static java.util.Arrays.asList;

public class IsNativeType {
    private static final IsNativeType INSTANCE = new IsNativeType();
    private static final List<? extends Class<? extends Serializable>> NATIVE_TYPES = asList(Integer.TYPE, Float.TYPE, Double.TYPE, Long.TYPE, Boolean.TYPE, Character.TYPE);

    public static IsNativeType instance () {
        return INSTANCE;
    }

    private IsNativeType() {
    }

    public boolean isNative(Class type) {
        return NATIVE_TYPES.contains(type);
    }
}
