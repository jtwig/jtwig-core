package org.jtwig.functions.resolver;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.functions.SimpleFunction;
import org.jtwig.functions.reference.FunctionReference;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.resolver.argument.ArgumentResolver;

import java.util.ArrayList;
import java.util.Collection;

public class FunctionResolverConfigurationBuilder implements Builder<FunctionResolverConfiguration> {
    private final Collection<Object> beans = new ArrayList<>();
    private final Collection<Converter> converters = new ArrayList<>();
    private final Collection<FunctionReference> functionReferences = new ArrayList<>();
    private final Collection<SimpleFunction> simpleFunctions = new ArrayList<>();
    private final Collection<ArgumentResolver> argumentResolvers = new ArrayList<>();

    public FunctionResolverConfigurationBuilder () {}
    public FunctionResolverConfigurationBuilder (FunctionResolverConfiguration prototype) {
        beans.addAll(prototype.getBeans());
        converters.addAll(prototype.getConverters());
        functionReferences.addAll(prototype.getFunctionReferences());
        simpleFunctions.addAll(prototype.getSimpleFunctions());
        argumentResolvers.addAll(prototype.getArgumentResolvers());
    }

    public FunctionResolverConfigurationBuilder withBeans(Collection<Object> beans) {
        this.beans.addAll(beans);
        return this;
    }

    public FunctionResolverConfigurationBuilder withConverters(Collection<Converter> converters) {
        this.converters.addAll(converters);
        return this;
    }

    public FunctionResolverConfigurationBuilder withFunctionReferences(Collection<FunctionReference> functionReferences) {
        this.functionReferences.addAll(functionReferences);
        return this;
    }

    public FunctionResolverConfigurationBuilder withSimpleFunctions(Collection<SimpleFunction> simpleFunctions) {
        this.simpleFunctions.addAll(simpleFunctions);
        return this;
    }

    public FunctionResolverConfigurationBuilder withArgumentResolvers(Collection<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers.addAll(argumentResolvers);
        return this;
    }

    @Override
    public FunctionResolverConfiguration build() {
        return new FunctionResolverConfiguration(beans, converters, functionReferences, simpleFunctions, argumentResolvers);
    }
}
