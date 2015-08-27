package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolver;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;

public class AnnotatedParameterResolver implements InputParameterResolver<FunctionArgument> {
    private final AnnotatedInputParameterResolver<FunctionArgument> delegate;

    public AnnotatedParameterResolver(AnnotatedInputParameterResolver<FunctionArgument> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Optional<Value> resolve(JavaMethodArgument argument, InputParameterResolverContext<FunctionArgument> context, Class to) {
        Optional<Parameter> annotation = argument.annotation(Parameter.class);
        if (annotation.isPresent()) {
            return delegate.resolve(annotation.get(), argument, context, to);
        }
        return Optional.absent();
    }
}
