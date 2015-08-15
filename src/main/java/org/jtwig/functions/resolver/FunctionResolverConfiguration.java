package org.jtwig.functions.resolver;

import org.jtwig.functions.SimpleFunction;
import org.jtwig.functions.reference.FunctionReference;
import org.jtwig.reflection.resolver.argument.ArgumentResolver;

import java.util.Collection;

public class FunctionResolverConfiguration {
    private final Collection<Object> beans;
    private final Collection<FunctionReference> functionReferences;
    private final Collection<SimpleFunction> simpleFunctions;
    private final Collection<ArgumentResolver> argumentResolvers;

    public FunctionResolverConfiguration(Collection<Object> beans, Collection<FunctionReference> functionReferences, Collection<SimpleFunction> simpleFunctions, Collection<ArgumentResolver> argumentResolvers) {
        this.beans = beans;
        this.functionReferences = functionReferences;
        this.simpleFunctions = simpleFunctions;
        this.argumentResolvers = argumentResolvers;
    }

    public Collection<Object> getBeans() {
        return beans;
    }

    public Collection<FunctionReference> getFunctionReferences() {
        return functionReferences;
    }

    public Collection<SimpleFunction> getSimpleFunctions() {
        return simpleFunctions;
    }

    public Collection<ArgumentResolver> getArgumentResolvers() {
        return argumentResolvers;
    }
}