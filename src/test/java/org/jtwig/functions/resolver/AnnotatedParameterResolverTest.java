package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolver;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnnotatedParameterResolverTest {
    private final AnnotatedInputParameterResolver<FunctionArgument> delegate = mock(AnnotatedInputParameterResolver.class);
    private AnnotatedParameterResolver underTest = new AnnotatedParameterResolver(delegate);

    @Test
    public void resolveWhenNoAnnotation() throws Exception {
        JavaMethodArgument argument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(argument.annotation(Parameter.class)).thenReturn(Optional.<Parameter>absent());

        Optional<FunctionArgument> result = underTest.resolve(argument, context);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenAnnotation() throws Exception {
        Parameter parameter = mock(Parameter.class);
        Optional<FunctionArgument> value = Optional.<FunctionArgument>absent();
        JavaMethodArgument argument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(argument.annotation(Parameter.class)).thenReturn(Optional.of(parameter));
        when(delegate.resolve(parameter, argument, context)).thenReturn(value);

        Optional<FunctionArgument> result = underTest.resolve(argument, context);

        assertSame(result, value);
    }
}