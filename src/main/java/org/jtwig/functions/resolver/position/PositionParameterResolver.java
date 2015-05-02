package org.jtwig.functions.resolver.position;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;

public interface PositionParameterResolver {
    Optional<FunctionArgument> resolve(JavaMethodArgument javaMethodArgument, int position, InputParameterResolverContext<FunctionArgument> context);
}
