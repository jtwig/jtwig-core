package org.jtwig.functions.resolver.position;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;

import java.util.Collection;

public class CompositePositionParameterResolver implements PositionParameterResolver {
    private final Collection<PositionParameterResolver> positionParameterResolvers;

    public CompositePositionParameterResolver(Collection<PositionParameterResolver> positionParameterResolvers) {
        this.positionParameterResolvers = positionParameterResolvers;
    }

    @Override
    public Optional<Value> resolve(JavaMethodArgument javaMethodArgument, int position, InputParameterResolverContext<FunctionArgument> context, Class to) {
        for (PositionParameterResolver positionParameterResolver : positionParameterResolvers) {
            Optional<Value> resolve = positionParameterResolver.resolve(javaMethodArgument, position, context, to);
            if (resolve.isPresent()) {
                return resolve;
            }
        }
        return Optional.absent();
    }
}
