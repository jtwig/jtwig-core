package org.jtwig.value.converter.collection;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.Undefined;

import java.util.Collections;

public class NullCollectionConverter extends CollectionConverter {
    public NullCollectionConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value == null || value == Undefined.UNDEFINED) {
                    return Optional.of(new Value(Collections.emptyList()));
                }
                return Optional.absent();
            }
        });
    }
}
