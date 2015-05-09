package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ArrayBooleanExtractorTest {
    private ArrayBooleanExtractor underTest = new ArrayBooleanExtractor();

    @Test
    public void extractWhenNonArray() throws Exception {
        Optional<Boolean> result = underTest.extract(new Object());

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractWhenEmptyArray() throws Exception {
        Optional<Boolean> result = underTest.extract(new Object[]{});

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }

    @Test
    public void extractWhenWithItemsArray() throws Exception {
        Optional<Boolean> result = underTest.extract(new Object[]{null});

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(true));
    }
}