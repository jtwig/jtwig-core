package org.jtwig.value.extract.type;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.jtwig.value.extract.CompositeExtractor;
import org.jtwig.value.extract.Extractor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeTypeExtractorTest {
    private Collection<Extractor<Object>> extractors = new ArrayList<>();
    private CompositeExtractor<Object> underTest = new CompositeExtractor<>(extractors);

    @Before
    public void setUp() throws Exception {
        extractors.clear();
    }

    @Test
    public void extractWhenNoneApplies() throws Exception {
        Object input = new Object();
        Extractor typeExtractor = mock(Extractor.class);
        when(typeExtractor.extract(input)).thenReturn(Optional.<JtwigType>absent());
        extractors.add(typeExtractor);

        Optional<Object> result = underTest.extract(input);

        assertThat(result.isPresent(), is(false));
    }
}