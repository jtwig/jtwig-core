package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NamedInputParameterResolverTest {
    private NamedInputParameterResolver underTest = new NamedInputParameterResolver();

    @Test
    public void resolveWhenNotExists() throws Exception {
        Parameter parameter = mock(Parameter.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);

        Optional<FunctionArgument> result = underTest.resolve(parameter, javaMethodArgument, context);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenExistsAndEquals() throws Exception {
        Parameter parameter = mock(Parameter.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        when(context.size()).thenReturn(1);
        when(parameter.value()).thenReturn("hello");
        when(context.value(0)).thenReturn(functionArgument);
        when(functionArgument.getName()).thenReturn(Optional.of("hello"));

        Optional<FunctionArgument> result = underTest.resolve(parameter, javaMethodArgument, context);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(functionArgument));
    }

    @Test
    public void resolveWhenExistsAndDifferent() throws Exception {
        Parameter parameter = mock(Parameter.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        when(context.size()).thenReturn(1);
        when(parameter.value()).thenReturn("hello");
        when(context.value(0)).thenReturn(functionArgument);
        when(functionArgument.getName()).thenReturn(Optional.of("hello1"));

        Optional<FunctionArgument> result = underTest.resolve(parameter, javaMethodArgument, context);

        assertThat(result.isPresent(), is(false));
    }
}