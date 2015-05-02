package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolver;
import org.jtwig.reflection.input.InputParameterResolverFactory;
import org.jtwig.reflection.model.java.JavaMethod;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.functions.resolver.position.CompositePositionParameterResolver;
import org.jtwig.functions.resolver.position.SimplePositionParameterResolver;
import org.jtwig.functions.resolver.position.VarArgsPositionParameterResolver;
import org.jtwig.functions.resolver.position.vararg.ArrayComponentExtractor;
import org.jtwig.functions.resolver.position.vararg.FromPositionExtractor;
import org.jtwig.functions.resolver.position.vararg.FunctionArgumentMerger;

import java.util.List;

import static java.util.Arrays.asList;

public class FunctionArgumentResolverFactory implements InputParameterResolverFactory<FunctionArgument> {
    @Override
    public InputParameterResolver<FunctionArgument> create(JavaMethod javaMethod, List<FunctionArgument> list) {
        if (supportsNamedParameters(javaMethod) && supportsNamedArguments(list)) {
            return new AnnotatedParameterResolver(new NamedInputParameterResolver());
        } else {
            return new AnnotatedParameterResolver(new PositionInputParameterResolver(
                    new CompositePositionParameterResolver(
                            asList(
                                    new SimplePositionParameterResolver(),
                                    new VarArgsPositionParameterResolver(new FunctionArgumentMerger(new ArrayComponentExtractor()), new FromPositionExtractor())
                            )
                    )
            ));
        }
    }

    private boolean supportsNamedArguments(List<FunctionArgument> list) {
        for (FunctionArgument functionArgument : list) {
            if (!functionArgument.getName().isPresent()) {
                return false;
            }
        }
        return true;
    }

    private boolean supportsNamedParameters(JavaMethod javaMethod) {
        for (JavaMethodArgument javaMethodArgument : javaMethod.arguments()) {
            Optional<Parameter> parameter = javaMethodArgument.annotation(Parameter.class);
            if (parameter.isPresent()) {
                if (StringUtils.isBlank(parameter.get().value())) {
                    return false;
                }
            }
        }
        return true;
    }
}
