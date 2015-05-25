package org.jtwig.functions.resolver;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.functions.SimpleFunction;
import org.jtwig.functions.reference.FunctionReference;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.resolver.argument.ArgumentResolver;

import java.util.ArrayList;
import java.util.Collection;

public class FunctionResolverConfigurationBuilder<B extends FunctionResolverConfigurationBuilder> implements Builder<FunctionResolverConfiguration> {
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
        return self();
    }

    public FunctionResolverConfigurationBuilder withConverters(Collection<Converter> converters) {
        this.converters.addAll(converters);
        return self();
    }

    public FunctionResolverConfigurationBuilder withFunctionReferences(Collection<FunctionReference> functionReferences) {
        this.functionReferences.addAll(functionReferences);
        return self();
    }

    public FunctionResolverConfigurationBuilder withSimpleFunctions(Collection<SimpleFunction> simpleFunctions) {
        this.simpleFunctions.addAll(simpleFunctions);
        return self();
    }

    public FunctionResolverConfigurationBuilder withArgumentResolvers(Collection<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers.addAll(argumentResolvers);
        return self();
    }

    public B include(SimpleFunction simpleFunction) {
        this.simpleFunctions.add(simpleFunction);
        return self();
    }

    protected B self () {
        return (B) this;
    }

    @Override
    public FunctionResolverConfiguration build() {
        return new FunctionResolverConfiguration(beans, converters, functionReferences, simpleFunctions, argumentResolvers);
    }
}
