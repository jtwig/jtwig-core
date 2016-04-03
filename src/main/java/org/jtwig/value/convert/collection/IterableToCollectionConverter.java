package org.jtwig.value.convert.collection;

import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

import java.util.Iterator;

public class IterableToCollectionConverter implements Converter<WrappedCollection> {
    @Override
    public Result<WrappedCollection> convert(Object object) {
        if (object instanceof Iterable) {
            WrappedCollection result = new WrappedCollection();
            Iterator iterator = ((Iterable) object).iterator();
            int i = 0;
            while (iterator.hasNext()) {
                Object next = iterator.next();
                result.add(String.valueOf(i++), next);
            }
            return Result.defined(result);
        }
        return Result.undefined();
    }
}
