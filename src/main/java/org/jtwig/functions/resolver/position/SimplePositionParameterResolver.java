package org.jtwig.functions.resolver.position;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;

public class SimplePositionParameterResolver implements PositionParameterResolver {
    @Override
    public Optional<FunctionArgument> resolve(JavaMethodArgument javaMethodArgument, int position, InputParameterResolverContext<FunctionArgument> context) {
        if (!javaMethodArgument.isVarArg()) {
            if (!context.isUsed(position)) {
                context.markAsUsed(position);
                return Optional.fromNullable(context.value(position));
            } else {
                return Optional.absent();
            }
        } else {
            return Optional.absent();
        }
    }
}
