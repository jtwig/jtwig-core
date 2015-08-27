package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.functions.resolver.position.PositionParameterResolver;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.reflection.util.Lists2;

import java.util.List;

public class PositionInputParameterResolver implements AnnotatedInputParameterResolver<FunctionArgument> {
    private final PositionParameterResolver positionParameterResolver;

    public PositionInputParameterResolver(PositionParameterResolver positionParameterResolver) {
        this.positionParameterResolver = positionParameterResolver;
    }

    @Override
    public Optional<Value> resolve(Parameter parameter, JavaMethodArgument javaMethodArgument, InputParameterResolverContext<FunctionArgument> context, Class to) {
        List<JavaMethodArgument> arguments = onlyArguments(javaMethodArgument);
        int position = position(javaMethodArgument, arguments);
        return positionParameterResolver.resolve(javaMethodArgument, position, context, to);
    }

    private int position(final JavaMethodArgument javaMethodArgument, List<JavaMethodArgument> arguments) {
        return Lists2.positionOf(arguments, new Predicate<JavaMethodArgument>() {
            @Override
            public boolean apply(JavaMethodArgument input) {
                return input == javaMethodArgument;
            }
        });
    }

    private List<JavaMethodArgument> onlyArguments(JavaMethodArgument javaMethodArgument) {
        List<JavaMethodArgument> arguments = javaMethodArgument.method().arguments();
        return Lists2.filter(arguments, new Predicate<JavaMethodArgument>() {
            @Override
            public boolean apply(JavaMethodArgument input) {
                return input.annotation(Parameter.class).isPresent();
            }
        });
    }
}
