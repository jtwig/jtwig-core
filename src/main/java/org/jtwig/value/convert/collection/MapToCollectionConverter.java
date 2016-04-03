package org.jtwig.value.convert.collection;

import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

import java.util.Map;

public class MapToCollectionConverter implements Converter<WrappedCollection> {
    @Override
    public Result<WrappedCollection> convert(Object object) {
        if (object instanceof Map) {
            Map<Object, Object> input = (Map) object;
            WrappedCollection result = new WrappedCollection();
            for (Map.Entry<Object, Object> entry : input.entrySet()) {
                result.add(entry.getKey().toString(), entry.getValue());
            }
            return Result.defined(result);
        }
        return Result.undefined();
    }
}
