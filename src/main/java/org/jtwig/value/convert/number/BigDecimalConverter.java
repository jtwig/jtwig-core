package org.jtwig.value.convert.number;

import org.jtwig.value.Undefined;
import org.jtwig.value.convert.Converter;

import java.math.BigDecimal;

public class BigDecimalConverter implements Converter<BigDecimal> {
    @Override
    public Result<BigDecimal> convert(Object object) {
        if (object == null) return Result.defined(BigDecimal.ZERO);
        if (object == Undefined.UNDEFINED) return Result.defined(BigDecimal.ZERO);

        if (object instanceof BigDecimal) return Result.defined((BigDecimal) object);
        if (object instanceof Number) return Result.defined(new BigDecimal(((Number) object).doubleValue()));
        if (object instanceof Boolean) return Result.defined(((boolean) object) ? BigDecimal.ONE : BigDecimal.ZERO);

        try {
            if (object instanceof String) return Result.defined(new BigDecimal((String) object));
        } catch (NumberFormatException e) {

        }
        return Result.undefined();
    }
}
