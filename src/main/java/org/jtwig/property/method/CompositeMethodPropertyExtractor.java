package org.jtwig.property.method;

import com.google.common.base.Optional;
import org.jtwig.property.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethod;

import java.util.Collection;

public class CompositeMethodPropertyExtractor implements MethodPropertyExtractor {
    private final Collection<MethodPropertyExtractor> methodPropertyExtractors;

    public CompositeMethodPropertyExtractor(Collection<MethodPropertyExtractor> methodPropertyExtractors) {
        this.methodPropertyExtractors = methodPropertyExtractors;
    }

    @Override
    public Optional<Value> extract(PropertyResolveRequest request, JavaMethod method) {
        for (MethodPropertyExtractor methodPropertyExtractor : methodPropertyExtractors) {
            Optional<Value> result = methodPropertyExtractor.extract(request, method);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.absent();
    }
}
