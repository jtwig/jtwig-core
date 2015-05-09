package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class NullBooleanExtractorTest {
    private NullBooleanExtractor underTest = new NullBooleanExtractor();

    @Test
    public void extractWhenNotNull() throws Exception {

        Optional<Boolean> result = underTest.extract(new Object());

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractWhenNull() throws Exception {

        Optional<Boolean> result = underTest.extract(null);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }

    @Test
    public void extractWhenUndefined() throws Exception {

        Optional<Boolean> result = underTest.extract(Undefined.UNDEFINED);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }
}