package org.jtwig.value.extract.map;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class NullMapExtractorTest {
    private NullMapExtractor underTest = new NullMapExtractor();

    @Test
    public void extractWhenUndefined() throws Exception {
        Optional<Map> result = underTest.extract(Undefined.UNDEFINED);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().isEmpty(), is(true));
    }

    @Test
    public void extractWhenNull() throws Exception {
        Optional<Map> result = underTest.extract(null);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().isEmpty(), is(true));
    }
}