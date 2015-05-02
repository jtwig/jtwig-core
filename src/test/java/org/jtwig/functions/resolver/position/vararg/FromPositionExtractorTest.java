package org.jtwig.functions.resolver.position.vararg;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.functions.FunctionArgument;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FromPositionExtractorTest {
    private FromPositionExtractor underTest = new FromPositionExtractor();

    @Test
    public void extractWhenNoneUsed() throws Exception {
        int position = 0;
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(context.size()).thenReturn(1);
        when(context.isUsed(0)).thenReturn(false);
        when(context.value(0)).thenReturn(functionArgument);

        Optional<List<FunctionArgument>> result = underTest.extract(position, context);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), hasItem(functionArgument));
        verify(context).markAsUsed(0);
    }

    @Test
    public void extractWhenAtLeastOneUsed() throws Exception {
        int position = 0;
        FunctionArgument functionArgument = mock(FunctionArgument.class);
        InputParameterResolverContext<FunctionArgument> context = mock(InputParameterResolverContext.class);
        when(context.size()).thenReturn(1);
        when(context.isUsed(0)).thenReturn(true);
        when(context.value(0)).thenReturn(functionArgument);

        Optional<List<FunctionArgument>> result = underTest.extract(position, context);

        assertThat(result.isPresent(), is(false));
        verify(context, never()).markAsUsed(0);
    }
}