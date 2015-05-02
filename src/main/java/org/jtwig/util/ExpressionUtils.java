package org.jtwig.util;

import com.google.common.base.Optional;

public final class ExpressionUtils {

    private ExpressionUtils() {
    }

    public static boolean isTrue(Object value) {
        if (value == null) {
            return false;
        } else {
            if (value.getClass().isArray()) {
                return ((Object[]) value).length > 0;
            } else if (value instanceof Iterable) {
                return ((Iterable) value).iterator().hasNext();
            } else if (value instanceof Boolean) {
                return (Boolean) value;
            } else if (value instanceof String) {
                return !((String) value).isEmpty();
            } else if (value instanceof Number) {
                return ((Number) value).intValue() != 0;
            }
        }
        return true;
    }

    public static Optional<String> numberAsString (Object value) {
        if (value == null) {
            return Optional.of(Integer.toString(0));
        } else {
            if (value instanceof Boolean) {
                return Optional.of((Boolean.class.cast(value)) ? Integer.toString(1) : Integer.toString(0));
            } else if (value instanceof Number) {
                return Optional.of(value.toString());
            } else {
                String representation = value.toString();
                if (representation.matches("-?\\d*\\.?\\d+")) {
                    return Optional.of(representation);
                } else {
                    return Optional.absent();
                }
            }
        }
    }
}
