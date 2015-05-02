package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.util.JtwigValue;

import java.util.Collection;

public interface PropertyResolver {
    Optional<JtwigValue> resolve(PropertyResolveRequest request);
}
