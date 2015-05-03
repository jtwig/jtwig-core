package org.jtwig.util;

import com.google.common.base.Supplier;

public class OptionalUtils {
    public static <T, E extends RuntimeException> Supplier<T> throwException (final E exception) {
        return new Supplier<T>() {
            @Override
            public T get() {
                throw exception;
            }
        };
    }
}
