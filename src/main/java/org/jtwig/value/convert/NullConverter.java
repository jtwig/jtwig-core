package org.jtwig.value.convert;

public class NullConverter<T> implements Converter<T> {
    @Override
    public Result<T> convert(Object object) {
        if (object == null) return Result.defined(null);

        return Result.undefined();
    }
}
