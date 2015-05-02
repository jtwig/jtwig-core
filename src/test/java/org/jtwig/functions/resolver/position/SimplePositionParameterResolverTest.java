package org.jtwig.functions.resolver.position;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.junit.Test;

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

        Optional<FunctionArgument> result = underTest.resolve(javaMethodArgument, position, context);

        assertThat(result.isPresent(), is(false));
        verify(context, never()).markAsUsed(position);
    }

    @Test
    public void resolveWhenUsed() throws Exception {
        int position = 0;
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(javaMethodArgument.isVarArg()).thenReturn(false);
        when(context.isUsed(position)).thenReturn(true);

        Optional<FunctionArgument> result = underTest.resolve(javaMethodArgument, position, context);

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
        when(context.isUsed(position)).thenReturn(false);
        when(context.value(position)).thenReturn(functionArgument);

        Optional<FunctionArgument> result = underTest.resolve(javaMethodArgument, position, context);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(functionArgument));
        verify(context).markAsUsed(position);
    }
}