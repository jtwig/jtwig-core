package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;

public interface PropertyResolver {
    Optional<Value> resolve(PropertyResolveRequest request);
}