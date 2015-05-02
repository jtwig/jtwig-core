package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;

public interface AnnotatedInputParameterResolver<T> {
    Optional<FunctionArgument> resolve(Parameter parameter, JavaMethodArgument javaMethodArgument, InputParameterResolverContext<FunctionArgument> context);
}
