package org.jtwig.value.extract.type;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeTypeExtractorTest {
    private Collection<TypeExtractor> extractors = new ArrayList<>();
    private CompositeTypeExtractor underTest = new CompositeTypeExtractor(extractors);

    @Before
    public void setUp() throws Exception {
        extractors.clear();
    }

    @Test
    public void extractWhenNoneApplies() throws Exception {
        Object input = new Object();
        TypeExtractor typeExtractor = mock(TypeExtractor.class);
        when(typeExtractor.extract(input)).thenReturn(Optional.<JtwigType>absent());
        extractors.add(typeExtractor);

        Optional<JtwigType> result = underTest.extract(input);

        assertThat(result.isPresent(), is(false));
    }
}