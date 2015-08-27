package org.jtwig.functions.resolver.position;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;

public interface PositionParameterResolver {
    Optional<Value> resolve(JavaMethodArgument javaMethodArgument, int position, InputParameterResolverContext<FunctionArgument> context, Class to);
}
