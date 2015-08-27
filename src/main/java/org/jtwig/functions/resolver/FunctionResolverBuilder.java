package org.jtwig.functions.resolver;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.functions.reference.CompositeFunctionReference;
import org.jtwig.functions.reference.FunctionReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FunctionResolverBuilder implements Builder<FunctionResolver> {
    private final Map<String, Collection<FunctionReference>> references = new HashMap<>();

    public FunctionResolverBuilder with (FunctionReference reference) {
        if (!references.containsKey(reference.name())) {
            references.put(reference.name(), new ArrayList<FunctionReference>());
        }
        references.get(reference.name()).add(reference);
        return this;
    }

    public FunctionResolverBuilder with (Collection<FunctionReference> references) {
        for (FunctionReference reference : references) {
            with(reference);
        }
        return this;
    }

    @Override
    public FunctionResolver build() {
        Map<String, FunctionReference> functionMap = new HashMap<>();
        for (Map.Entry<String, Collection<FunctionReference>> entry : references.entrySet()) {
            functionMap.put(entry.getKey(), toJtwigFunction(entry.getKey(), entry.getValue()));
        }
        return new CoreFunctionResolver(functionMap);
    }

    private FunctionReference toJtwigFunction(String name, Collection<FunctionReference> functionCollection) {
        if (functionCollection.size() == 1) {
            return functionCollection.iterator().next();
        } else {
            return new CompositeFunctionReference(name, functionCollection);
        }
    }

}
