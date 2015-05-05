package org.jtwig.value.extract.collection;

import com.google.common.base.Optional;

import java.util.Collection;

public interface CollectionExtractor {
    Optional<Collection<Object>> extract (Object value);
}
