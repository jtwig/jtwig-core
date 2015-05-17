package org.jtwig.value.extract.string;

import com.google.common.base.Optional;

public interface StringExtractor {
    Optional<String> extract (Object value);
}
