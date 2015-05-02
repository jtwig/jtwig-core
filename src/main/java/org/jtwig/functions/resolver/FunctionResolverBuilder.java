package org.jtwig.functions.resolver;

import org.jtwig.reflection.MethodInvoker;
import org.jtwig.reflection.MethodInvokerBuilder;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.extractor.BeanMethodExtractor;
import org.jtwig.reflection.resolver.argument.ArgumentResolver;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.SimpleFunction;
import org.jtwig.functions.extract.BeanFunctionReferenceExtractor;
import org.jtwig.functions.extract.BeanMethodFunctionExtractor;
import org.jtwig.functions.extract.FunctionNameExtractor;
import org.jtwig.functions.reference.CompositeFunctionReference;
import org.jtwig.functions.reference.FunctionReference;
import org.jtwig.functions.reference.SimpleFunctionReferenceAdapter;
import org.jtwig.util.CompletableSupplier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FunctionResolverBuilder implements Builder<FunctionResolver> {
    public static FunctionResolverBuilder newBuilder () {
        return new FunctionResolverBuilder();
    }

    private final Map<String, Collection<FunctionReference>> functions;
    private final BeanFunctionReferenceExtractor extractor;
    private final MethodInvokerBuilder<FunctionArgument> methodInvokerBuilder;
    private final CompletableSupplier<MethodInvoker<FunctionArgument>> methodInvokerCompletableSupplier;

    FunctionResolverBuilder(Map<String, Collection<FunctionReference>> functions, BeanFunctionReferenceExtractor extractor, MethodInvokerBuilder<FunctionArgument> methodInvokerBuilder) {
        this.functions = functions;
        this.extractor = extractor;
        this.methodInvokerBuilder = methodInvokerBuilder;
        methodInvokerCompletableSupplier = new CompletableSupplier<>();
    }

    private FunctionResolverBuilder() {
        functions = new HashMap<>();
        methodInvokerCompletableSupplier = new CompletableSupplier<>();
        extractor = new BeanFunctionReferenceExtractor(new BeanMethodExtractor(), new BeanMethodFunctionExtractor(methodInvokerCompletableSupplier, new FunctionNameExtractor()));
        methodInvokerBuilder = new MethodInvokerBuilder<FunctionArgument>()
            .withInputParameterResolverFactory(new FunctionArgumentResolverFactory())
            .withInputParameterValueResolver(new FunctionArgumentValueResolver());
    }

    public FunctionResolverBuilder withArgumentResolver (ArgumentResolver argumentResolver) {
        methodInvokerBuilder.withArgumentResolver(argumentResolver);
        return this;
    }

    public FunctionResolverBuilder withArgumentResolvers (Collection<ArgumentResolver> argumentResolver) {
        methodInvokerBuilder.withArgumentResolvers(argumentResolver);
        return this;
    }

    public FunctionResolverBuilder withConverter (Converter converter) {
        methodInvokerBuilder.withConverter(converter);
        return this;
    }

    public FunctionResolverBuilder withConverters (Collection<Converter> converter) {
        methodInvokerBuilder.withConverters(converter);
        return this;
    }

    public FunctionResolverBuilder include (Object bean) {
        Collection<FunctionReference> functionReferences = extractor.extract(bean, methodInvokerCompletableSupplier);
        for (FunctionReference functionReference : functionReferences) {
            include(functionReference);
        }
        return this;
    }

    public FunctionResolverBuilder include (FunctionReference reference) {
        if (!functions.containsKey(reference.name())) {
            functions.put(reference.name(), new ArrayList<FunctionReference>());
        }
        functions.get(reference.name()).add(reference);
        return this;
    }

    public FunctionResolverBuilder include (SimpleFunction function) {
        return include(new SimpleFunctionReferenceAdapter(function));
    }

    @Override
    public FunctionResolver build() {
        methodInvokerCompletableSupplier.complete(methodInvokerBuilder.build());
        Map<String, FunctionReference> functionMap = new HashMap<>();
        for (Map.Entry<String, Collection<FunctionReference>> entry : functions.entrySet()) {
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
