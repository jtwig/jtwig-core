package org.jtwig.value.convert.collection;

import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

public class ArrayToCollectionConverter implements Converter<WrappedCollection> {
    @Override
    public Result<WrappedCollection> convert(Object object) {
        if (object.getClass().isArray()) {
            WrappedCollection result = new WrappedCollection();
            Object[] array = (Object[]) object;

            for (int i = 0; i < array.length; i++) {
                result.add(String.valueOf(i), array[i]);
            }

            return Result.defined(result);
        }
        return Result.undefined();
    }
}
