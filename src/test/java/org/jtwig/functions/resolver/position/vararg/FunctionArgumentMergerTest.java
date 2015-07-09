package org.jtwig.functions.resolver.position.vararg;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FunctionArgumentMergerTest {
    private final ArrayComponentExtractor arrayComponentExtractor = mock(ArrayComponentExtractor.class);
    private FunctionArgumentMerger underTest = new FunctionArgumentMerger(arrayComponentExtractor);

    @Test
    public void mergeWhenEmpty() throws Exception {
        List<FunctionArgument> input = Collections.emptyList();

        Optional<Value> result = underTest.merge(input);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getValue(), nullValue());
    }

    @Test
    public void mergeWhenNonEmpty() throws Exception {
        FunctionArgument functionArgument1 = mock(FunctionArgument.class);
        FunctionArgument functionArgument2 = mock(FunctionArgument.class);
        when(functionArgument1.getValue()).thenReturn(JtwigValueFactory.value(null, new DefaultValueConfiguration()));
        when(functionArgument2.getValue()).thenReturn(JtwigValueFactory.value(null, new DefaultValueConfiguration()));
        List<FunctionArgument> input = asList(functionArgument1, functionArgument2);
        when(arrayComponentExtractor.extract(input)).thenReturn(Object.class);

        Optional<Value> result = underTest.merge(input);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getValue(), notNullValue());
    }
}