package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public interface PropertyResolver {
    Optional<Value> resolve(PropertyResolveRequest request);
}
