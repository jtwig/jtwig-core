package org.jtwig.value.extract.type;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;

public interface TypeExtractor {
    Optional<JtwigType> extract (Object value);
}
