package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
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

        Optional<Value> result = underTest.resolve(argument, context, String.class);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenAnnotation() throws Exception {
        Parameter parameter = mock(Parameter.class);
        Optional<Value> value = Optional.absent();
        JavaMethodArgument argument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(argument.annotation(Parameter.class)).thenReturn(Optional.of(parameter));
        when(delegate.resolve(parameter, argument, context, String.class)).thenReturn(value);

        Optional<Value> result = underTest.resolve(argument, context, String.class);

        assertSame(result, value);
    }
}