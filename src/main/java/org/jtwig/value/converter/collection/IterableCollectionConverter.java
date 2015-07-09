package org.jtwig.value.converter.collection;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

import java.util.ArrayList;
import java.util.Collection;

public class IterableCollectionConverter extends CollectionConverter {
    public IterableCollectionConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value instanceof Iterable) {
                    Collection<Object> result = new ArrayList<>();
                    Iterable iterable = (Iterable) value;
                    for (Object item : iterable) {
                        result.add(item);
                    }
                    return Optional.of(new Value(result));
                }
                return Optional.absent();
            }
        });
    }
}
