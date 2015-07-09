package org.jtwig.functions.resolver.position;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;

public class SimplePositionParameterResolver implements PositionParameterResolver {
    @Override
    public Optional<Value> resolve(JavaMethodArgument javaMethodArgument, int position, InputParameterResolverContext<FunctionArgument> context, Class to) {
        if (!javaMethodArgument.isVarArg()) {
            if (context.size() > position) {
                if (!context.isUsed(position)) {
                    context.markAsUsed(position);
                    return context.value(position).getValue().as(to);
                } else {
                    return Optional.absent();
                }
            } else {
                return Optional.absent();
            }
        } else {
            return Optional.absent();
        }
    }
}
