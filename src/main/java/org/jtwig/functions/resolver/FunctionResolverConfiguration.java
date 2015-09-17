package org.jtwig.functions.resolver;

import org.jtwig.functions.JtwigFunction;

import java.util.Collection;

public class FunctionResolverConfiguration {
    private final Collection<JtwigFunction> functions;

    public FunctionResolverConfiguration(Collection<JtwigFunction> functions) {
        this.functions = functions;
    }

    public Collection<JtwigFunction> getFunctions() {
        return functions;
    }
}
