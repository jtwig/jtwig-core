package org.jtwig.value.converter.collection;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

import static java.util.Arrays.asList;

public class ArrayCollectionConverter extends CollectionConverter {
    public ArrayCollectionConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value.getClass().isArray()) {
                    return Optional.of(new Value(asList((Object[]) value)));
                }
                return Optional.absent();
            }
        });
    }
}
