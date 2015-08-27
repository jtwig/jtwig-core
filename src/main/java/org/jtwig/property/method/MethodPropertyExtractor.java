package org.jtwig.property.method;

import com.google.common.base.Optional;
import org.jtwig.property.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethod;

public interface MethodPropertyExtractor {
    Optional<Value> extract(PropertyResolveRequest request, JavaMethod method);
}
