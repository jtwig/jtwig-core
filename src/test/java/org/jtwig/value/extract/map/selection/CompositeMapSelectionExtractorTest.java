package org.jtwig.value.extract.map.selection;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeMapSelectionExtractorTest {
    private final ArrayList<MapSelectionExtractor> mapSelectionExtractors = new ArrayList<>();
    private final Map map = mock(Map.class);
    private final JtwigValue key = mock(JtwigValue.class);
    private CompositeMapSelectionExtractor underTest = new CompositeMapSelectionExtractor(mapSelectionExtractors);

    @Before
    public void setUp() throws Exception {
        mapSelectionExtractors.clear();
    }

    @Test
    public void resolveWhenNone() throws Exception {
        Optional<Value> result = underTest.extract(map, key);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenOneNotFinds() throws Exception {
        MapSelectionExtractor mapSelectionExtractor = mock(MapSelectionExtractor.class);
        mapSelectionExtractors.add(mapSelectionExtractor);
        when(mapSelectionExtractor.extract(map, key)).thenReturn(Optional.<Value>absent());

        Optional<Value> result = underTest.extract(map, key);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenOneFinds() throws Exception {
        Value expected = mock(Value.class);
        MapSelectionExtractor mapSelectionExtractor = mock(MapSelectionExtractor.class);
        mapSelectionExtractors.add(mapSelectionExtractor);
        when(mapSelectionExtractor.extract(map, key)).thenReturn(Optional.of(expected));

        Optional<Value> result = underTest.extract(map, key);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(expected));
    }
}