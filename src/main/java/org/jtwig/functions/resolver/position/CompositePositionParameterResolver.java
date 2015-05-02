package org.jtwig.functions.resolver.position;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;

import java.util.Collection;

public class CompositePositionParameterResolver implements PositionParameterResolver {
    private final Collection<PositionParameterResolver> positionParameterResolvers;

    public CompositePositionParameterResolver(Collection<PositionParameterResolver> positionParameterResolvers) {
        this.positionParameterResolvers = positionParameterResolvers;
    }

    @Override
    public Optional<FunctionArgument> resolve(JavaMethodArgument javaMethodArgument, int position, InputParameterResolverContext<FunctionArgument> context) {
        for (PositionParameterResolver positionParameterResolver : positionParameterResolvers) {
            Optional<FunctionArgument> resolve = positionParameterResolver.resolve(javaMethodArgument, position, context);
            if (resolve.isPresent()) {
                return resolve;
            }
        }
        return Optional.absent();
    }
}
