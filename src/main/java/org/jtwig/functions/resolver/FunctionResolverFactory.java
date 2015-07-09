package org.jtwig.functions.resolver;

import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.SimpleFunction;
import org.jtwig.functions.extract.BeanFunctionReferenceExtractor;
import org.jtwig.functions.extract.BeanMethodFunctionExtractor;
import org.jtwig.functions.extract.FunctionNameExtractor;
import org.jtwig.functions.reference.FunctionReference;
import org.jtwig.functions.reference.SimpleFunctionReferenceAdapter;
import org.jtwig.reflection.MethodInvoker;
import org.jtwig.reflection.MethodInvokerBuilder;
import org.jtwig.reflection.extractor.BeanMethodExtractor;

public class FunctionResolverFactory {
    public FunctionResolver create(FunctionResolverConfiguration configuration) {
        MethodInvoker<FunctionArgument> argumentMethodInvoker = new MethodInvokerBuilder<FunctionArgument>()
                .withInputParameterResolverFactory(new FunctionArgumentResolverFactory())
                .withInputParameterValueResolver(new FunctionArgumentValueResolver())
                .withArgumentResolvers(configuration.getArgumentResolvers())
                .build();
        BeanFunctionReferenceExtractor beanFunctionReferenceExtractor = new BeanFunctionReferenceExtractor(new BeanMethodExtractor(), new BeanMethodFunctionExtractor(argumentMethodInvoker, new FunctionNameExtractor()));
        FunctionResolverBuilder functionResolverBuilder = new FunctionResolverBuilder();
        for (Object bean : configuration.getBeans()) {
            functionResolverBuilder.with(beanFunctionReferenceExtractor.extract(bean));
        }

        for (FunctionReference functionReference : configuration.getFunctionReferences()) {
            functionResolverBuilder.with(functionReference);
        }
        for (SimpleFunction simpleFunction : configuration.getSimpleFunctions()) {
            functionResolverBuilder.with(new SimpleFunctionReferenceAdapter(simpleFunction));
        }

        return functionResolverBuilder.build();
    }
}
