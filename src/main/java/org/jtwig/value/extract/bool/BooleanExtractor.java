package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;

public interface BooleanExtractor {
    Optional<Boolean> extract (Object value);
}
