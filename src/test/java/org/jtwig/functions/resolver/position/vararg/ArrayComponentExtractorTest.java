package org.jtwig.functions.resolver.position.vararg;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ArrayComponentExtractorTest {
    private ArrayComponentExtractor underTest = new ArrayComponentExtractor();

    @Test
    public void extractWhenAllNull() throws Exception {
        List<FunctionArgument> input = asList(new FunctionArgument(Optional.<String>absent(), null));

        Class result = underTest.extract(input);

        assertThat(result, is(Object.class));
    }

    @Test
    public void extractWhenAtLeastOneIsNonNull() throws Exception {
        List<FunctionArgument> input = asList(new FunctionArgument(Optional.<String>absent(), ""));

        Class<?> result = underTest.extract(input);

        assertEquals(result, String.class);
    }
}