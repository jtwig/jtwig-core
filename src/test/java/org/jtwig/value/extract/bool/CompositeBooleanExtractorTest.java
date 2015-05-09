package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeBooleanExtractorTest {
    private Collection<BooleanExtractor> extractors = new ArrayList<>();
    private CompositeBooleanExtractor underTest = new CompositeBooleanExtractor(extractors);

    @Before
    public void setUp() throws Exception {
        extractors.clear();
    }

    @Test
    public void extractWhenNoneExtracts() throws Exception {
        Object value = new Object();
        BooleanExtractor booleanExtractor = mock(BooleanExtractor.class);
        when(booleanExtractor.extract(value)).thenReturn(Optional.<Boolean>absent());
        extractors.add(booleanExtractor);

        Optional<Boolean> result = underTest.extract(value);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractWhenOneExtracts() throws Exception {
        Object value = new Object();
        BooleanExtractor booleanExtractor = mock(BooleanExtractor.class);
        when(booleanExtractor.extract(value)).thenReturn(Optional.of(true));
        extractors.add(booleanExtractor);

        Optional<Boolean> result = underTest.extract(value);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(true));
    }
}