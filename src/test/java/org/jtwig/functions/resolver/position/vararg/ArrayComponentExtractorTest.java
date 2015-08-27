package org.jtwig.functions.resolver.position.vararg;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArrayComponentExtractorTest {
    private ArrayComponentExtractor underTest = new ArrayComponentExtractor();

    @Test
    public void extractWhenAllNull() throws Exception {
        JtwigValue jtwigValue = mock(JtwigValue.class);
        when(jtwigValue.isNull()).thenReturn(true);

        List<FunctionArgument> input = asList(new FunctionArgument(Optional.<String>absent(), jtwigValue));

        Class result = underTest.extract(input);

        assertThat(result, isA(Object.class));
    }

    @Test
    public void extractWhenAtLeastOneIsNonNull() throws Exception {
        JtwigValue jtwigValue = mock(JtwigValue.class);
        when(jtwigValue.asObject()).thenReturn("");
        when(jtwigValue.isNull()).thenReturn(false);
        when(jtwigValue.isDefined()).thenReturn(true);
        List<FunctionArgument> input = asList(new FunctionArgument(Optional.<String>absent(), jtwigValue));

        Class<?> result = underTest.extract(input);

        assertEquals(result, String.class);
    }
}