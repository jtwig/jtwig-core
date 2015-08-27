package org.jtwig.value.extract;

import com.google.common.base.Optional;

public interface Extractor<T> {
    Optional<T> extract (Object value);
}
