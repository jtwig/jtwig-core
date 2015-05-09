package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class IterableBooleanExtractorTest {
    private final IterableBooleanExtractor underTest = new IterableBooleanExtractor();

    @Test
    public void extractWhenNonIterable() throws Exception {

        Optional<Boolean> result = underTest.extract(new Object());

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractWhenIterableEmpty() throws Exception {

        Optional<Boolean> result = underTest.extract(new ArrayList());

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }

    @Test
    public void extractWhenIterableNonEmpty() throws Exception {

        Optional<Boolean> result = underTest.extract(new ArrayList() {{
            add("");
        }});

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(true));
    }
}