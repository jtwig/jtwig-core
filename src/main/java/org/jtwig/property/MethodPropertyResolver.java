package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.property.method.MethodPropertyExtractor;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethod;

import java.util.Collection;

public class MethodPropertyResolver implements PropertyResolver {
    private final MethodPropertyExtractor methodPropertyExtractor;

    public MethodPropertyResolver(MethodPropertyExtractor methodPropertyExtractor) {
        this.methodPropertyExtractor = methodPropertyExtractor;
    }

    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        Collection<JavaMethod> declaredMethods = request.getEntity().type().methods();
        for (JavaMethod declaredMethod : declaredMethods) {
            Optional<Value> result = methodPropertyExtractor.extract(request, declaredMethod);
            if (result.isPresent()) {
                return result;
            }
        }

        return Optional.absent();
    }

}
