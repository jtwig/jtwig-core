package org.jtwig.functions.resolver.position.vararg;

import org.jtwig.functions.FunctionArgument;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.NamedValueConfiguration;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FunctionArgumentMergerTest {
    private final ArrayComponentExtractor arrayComponentExtractor = mock(ArrayComponentExtractor.class);
    private FunctionArgumentMerger underTest = new FunctionArgumentMerger(arrayComponentExtractor);

    @Test
    public void mergeWhenEmpty() throws Exception {
        List<FunctionArgument> input = Collections.emptyList();

        FunctionArgument result = underTest.merge(input);

        assertThat(result.getValue(), nullValue());
    }

    @Test
    public void mergeWhenNonEmpty() throws Exception {
        FunctionArgument functionArgument1 = mock(FunctionArgument.class);
        FunctionArgument functionArgument2 = mock(FunctionArgument.class);
        when(functionArgument1.getValue()).thenReturn(JtwigValueFactory.value(null, NamedValueConfiguration.COMPATIBLE_MODE));
        when(functionArgument2.getValue()).thenReturn(JtwigValueFactory.value(null, NamedValueConfiguration.COMPATIBLE_MODE));
        List<FunctionArgument> input = asList(functionArgument1, functionArgument2);
        when(arrayComponentExtractor.extract(input)).thenReturn(Object.class);

        FunctionArgument result = underTest.merge(input);

        assertThat(result.getValue(), notNullValue());
    }
}