package org.jtwig.value.converter.collection;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.converter.TypeSpecificConverter;

import java.util.Collection;

public class CollectionConverter extends TypeSpecificConverter {
    public CollectionConverter(Function<Object, Optional<Value>> transform) {
        super(Collection.class, transform);
    }
}
