package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.value.JtwigValue;
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

        Optional<Value> result = underTest.resolve(parameter, javaMethodArgument, context, String.class);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenExistsAndEquals() throws Exception {
        Parameter parameter = mock(Parameter.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        JtwigValue jtwigValue = mock(JtwigValue.class);
        Optional<Value> expected = Optional.<Value>absent();
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        when(context.size()).thenReturn(1);
        when(parameter.value()).thenReturn("hello");
        when(context.value(0)).thenReturn(functionArgument);
        when(functionArgument.getValue()).thenReturn(jtwigValue);
        when(jtwigValue.as(String.class)).thenReturn(expected);
        when(functionArgument.getName()).thenReturn(Optional.of("hello"));

        Optional<Value> result = underTest.resolve(parameter, javaMethodArgument, context, String.class);

        assertSame(expected, result);
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

        Optional<Value> result = underTest.resolve(parameter, javaMethodArgument, context, String.class);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenArgumentUsed() throws Exception {
        Parameter parameter = mock(Parameter.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        when(context.size()).thenReturn(1);
        when(parameter.value()).thenReturn("hello");
        when(context.value(0)).thenReturn(functionArgument);
        when(context.isUsed(0)).thenReturn(true);
        when(functionArgument.getName()).thenReturn(Optional.of("hello"));

        Optional<Value> result = underTest.resolve(parameter, javaMethodArgument, context, String.class);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenArgumentNameNotPresent() throws Exception {
        Parameter parameter = mock(Parameter.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        when(context.size()).thenReturn(1);
        when(parameter.value()).thenReturn("hello");
        when(context.value(0)).thenReturn(functionArgument);
        when(context.isUsed(0)).thenReturn(false);
        when(functionArgument.getName()).thenReturn(Optional.<String>absent());

        Optional<Value> result = underTest.resolve(parameter, javaMethodArgument, context, String.class);

        assertThat(result.isPresent(), is(false));
    }
}