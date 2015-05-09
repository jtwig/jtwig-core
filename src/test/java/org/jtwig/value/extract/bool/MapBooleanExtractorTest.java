package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MapBooleanExtractorTest {
    private MapBooleanExtractor underTest = new MapBooleanExtractor();

    @Test
    public void extractWhenNotMap() throws Exception {

        Optional<Boolean> result = underTest.extract(new Object());

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractWhenMapEmpty() throws Exception {

        Optional<Boolean> result = underTest.extract(new HashMap<>());

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }

    @Test
    public void extractWhenMapNonEmpty() throws Exception {

        Optional<Boolean> result = underTest.extract(new HashMap() {{
            put("", "");
        }});

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(true));
    }
}