package org.jtwig.functions.resolver.position;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.resolver.position.vararg.FromPositionExtractor;
import org.jtwig.functions.resolver.position.vararg.FunctionArgumentMerger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VarArgsPositionParameterResolverTest {
    private final FunctionArgumentMerger functionArgumentMerger = mock(FunctionArgumentMerger.class);
    private final FromPositionExtractor fromPositionExtractor = mock(FromPositionExtractor.class);
    private VarArgsPositionParameterResolver underTest = new VarArgsPositionParameterResolver(functionArgumentMerger, fromPositionExtractor);

    @Test
    public void resolveWhenNonVarArgs() throws Exception {
        int position = 0;
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(javaMethodArgument.isVarArg()).thenReturn(false);

        Optional<FunctionArgument> result = underTest.resolve(javaMethodArgument, position, context);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenContextSizeEqualToPosition() throws Exception {
        int position = 0;
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(javaMethodArgument.isVarArg()).thenReturn(true);
        when(context.size()).thenReturn(position);

        Optional<FunctionArgument> result = underTest.resolve(javaMethodArgument, position, context);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getValue().isNull(), is(true));
    }

    @Test
    public void resolveWhenFromPositionReturnsAbsent() throws Exception {
        int position = 0;
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(javaMethodArgument.isVarArg()).thenReturn(true);
        when(context.size()).thenReturn(position+1);
        when(fromPositionExtractor.extract(position, context)).thenReturn(Optional.<List<FunctionArgument>>absent());

        Optional<FunctionArgument> result = underTest.resolve(javaMethodArgument, position, context);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenHappyPath() throws Exception {
        int position = 0;
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        when(javaMethodArgument.isVarArg()).thenReturn(true);
        when(context.size()).thenReturn(position+1);
        List<FunctionArgument> functionArguments = new ArrayList<>();
        when(fromPositionExtractor.extract(position, context)).thenReturn(Optional.of(functionArguments));
        when(functionArgumentMerger.merge(functionArguments)).thenReturn(functionArgument);


        Optional<FunctionArgument> result = underTest.resolve(javaMethodArgument, position, context);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(functionArgument));
    }
}