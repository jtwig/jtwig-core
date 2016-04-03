package org.jtwig.value.convert.bool;

import org.jtwig.value.Undefined;
import org.jtwig.value.convert.Converter;

public class BooleanConverter implements Converter<Boolean> {
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    @Override
    public Result<Boolean> convert(Object object) {
        if (object == null) return Result.defined(false);
        if (object == Undefined.UNDEFINED) return Result.defined(false);
        if (object instanceof Boolean) return Result.defined((Boolean) object);
        if (object instanceof String) {
            if (TRUE.equals(object)) {
                return Result.defined(true);
            } else if (FALSE.equals(object)) {
                return Result.defined(false);
            }
        }

        if (object.getClass().isArray()) {
            return Result.defined(((Object[]) object).length > 0);
        } else if (object instanceof Iterable) {
            return Result.defined(((Iterable) object).iterator().hasNext());
        } else if (object instanceof Number) {
            return Result.defined(((Number) object).intValue() != 0);
        } else if (object instanceof String) {
            return Result.defined(!((String) object).isEmpty());
        }

        return Result.undefined();
    }
}
