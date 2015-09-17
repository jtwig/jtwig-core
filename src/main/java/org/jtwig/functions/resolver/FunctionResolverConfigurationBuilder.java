package org.jtwig.functions.resolver;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.functions.JtwigFunction;

import java.util.ArrayList;
import java.util.Collection;

public class FunctionResolverConfigurationBuilder<B extends FunctionResolverConfigurationBuilder> implements Builder<FunctionResolverConfiguration> {
    private final Collection<JtwigFunction> functions = new ArrayList<>();

    public FunctionResolverConfigurationBuilder () {}
    public FunctionResolverConfigurationBuilder (FunctionResolverConfiguration prototype) {
        functions.addAll(prototype.getFunctions());
    }

    public B withFunctions(Collection<JtwigFunction> jtwigFunctions) {
        this.functions.addAll(jtwigFunctions);
        return self();
    }

    public B withFunction(JtwigFunction function) {
        this.functions.add(function);
        return self();
    }

    protected B self () {
        return (B) this;
    }

    @Override
    public FunctionResolverConfiguration build() {
        return new FunctionResolverConfiguration(functions);
    }
}
