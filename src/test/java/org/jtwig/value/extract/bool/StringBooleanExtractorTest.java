package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StringBooleanExtractorTest {
    private StringBooleanExtractor underTest = new StringBooleanExtractor();

    @Test
    public void extractWhenNotString() throws Exception {

        Optional<Boolean> result = underTest.extract(new Object());

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractWhenStringEmpty() throws Exception {

        Optional<Boolean> result = underTest.extract("");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }

    @Test
    public void extractWhenStringNonEmpty() throws Exception {

        Optional<Boolean> result = underTest.extract("a");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(true));
    }
}