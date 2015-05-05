package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;

public interface PropertyResolver {
    Optional<JtwigValue> resolve(PropertyResolveRequest request);
}
