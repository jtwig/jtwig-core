package org.jtwig.value.extract.collection;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class NullCollectionExtractorTest {
    private NullCollectionExtractor underTest = new NullCollectionExtractor();

    @Test
    public void extractWhenUndefined() throws Exception {
        Optional<Collection<Object>> result = underTest.extract(Undefined.UNDEFINED);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().isEmpty(), is(true));
    }

    @Test
    public void extractWhenNull() throws Exception {
        Optional<Collection<Object>> result = underTest.extract(null);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().isEmpty(), is(true));
    }
}