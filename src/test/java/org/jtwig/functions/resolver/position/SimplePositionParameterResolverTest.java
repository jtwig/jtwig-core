package org.jtwig.functions.resolver.position;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import static junit.framework.Assert.assertSame;
import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class SimplePositionParameterResolverTest {
    private SimplePositionParameterResolver underTest = new SimplePositionParameterResolver();

    @Test
    public void resolveWhenVarArg() throws Exception {
        int position = 0;
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(javaMethodArgument.isVarArg()).thenReturn(true);

        Optional<Value> result = underTest.resolve(javaMethodArgument, position, context, String.class);

        assertThat(result.isPresent(), is(false));
        verify(context, never()).markAsUsed(position);
    }

    @Test
    public void resolveWhenUsed() throws Exception {
        int position = 0;
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(context.size()).thenReturn(1);
        when(javaMethodArgument.isVarArg()).thenReturn(false);
        when(context.isUsed(position)).thenReturn(true);

        Optional<Value> result = underTest.resolve(javaMethodArgument, position, context, String.class);

        assertThat(result.isPresent(), is(false));
        verify(context, never()).markAsUsed(position);
    }

    @Test
    public void resolveWhenArgumentBeUsed() throws Exception {
        int position = 0;
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(context.size()).thenReturn(0);
        when(javaMethodArgument.isVarArg()).thenReturn(false);
        when(context.isUsed(position)).thenReturn(true);

        Optional<Value> result = underTest.resolve(javaMethodArgument, position, context, String.class);

        assertThat(result.isPresent(), is(false));
        verify(context, never()).markAsUsed(position);
    }

    @Test
    public void resolveWhenNotUsed() throws Exception {
        int position = 0;
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(javaMethodArgument.isVarArg()).thenReturn(false);
        when(context.size()).thenReturn(1);
        when(context.isUsed(position)).thenReturn(false);
        when(context.value(position)).thenReturn(functionArgument);
        JtwigValue value = mock(JtwigValue.class);
        when(functionArgument.getValue()).thenReturn(value);
        Optional<Value> expected = Optional.<Value>absent();
        when(value.as(String.class)).thenReturn(expected);

        Optional<Value> result = underTest.resolve(javaMethodArgument, position, context, String.class);

        assertSame(expected, result);
        verify(context).markAsUsed(position);
    }
}