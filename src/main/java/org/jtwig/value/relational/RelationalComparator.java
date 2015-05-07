package org.jtwig.value.relational;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;

public interface RelationalComparator {
    Optional<Boolean> apply (JtwigValue left, JtwigValue right);
}
