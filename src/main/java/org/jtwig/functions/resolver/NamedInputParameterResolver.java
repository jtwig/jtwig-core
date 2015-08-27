package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;

public class NamedInputParameterResolver implements AnnotatedInputParameterResolver<FunctionArgument> {
    @Override
    public Optional<Value> resolve(Parameter parameter, JavaMethodArgument javaMethodArgument, InputParameterResolverContext<FunctionArgument> context, Class to) {
        String name = parameter.value();
        for (int i = 0; i < context.size(); i++) {
            FunctionArgument functionArgument = context.value(i);
            if (!context.isUsed(i)) {
                if (functionArgument.getName().isPresent()) {
                    if (functionArgument.getName().get().equals(name)) {
                        context.markAsUsed(i);
                        return functionArgument.getValue().as(to);
                    }
                }
            }
        }

        return Optional.absent();
    }
}
