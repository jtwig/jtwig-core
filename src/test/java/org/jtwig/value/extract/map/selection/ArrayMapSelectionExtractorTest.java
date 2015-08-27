package org.jtwig.value.extract.map.selection;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArrayMapSelectionExtractorTest {
    private final Map map = mock(Map.class);
    private final JtwigValue key = mock(JtwigValue.class);
    private ArrayMapSelectionExtractor underTest = new ArrayMapSelectionExtractor();

    @Test
    public void resolveWhenNotNumber() throws Exception {
        when(key.asNumber()).thenReturn(Optional.<BigDecimal>absent());

        Optional<Value> result = underTest.extract(map, key);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenNumberNotPresentInMap() throws Exception {
        when(key.asNumber()).thenReturn(Optional.of(BigDecimal.ONE));
        when(map.containsKey(1)).thenReturn(false);

        Optional<Value> result = underTest.extract(map, key);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenNumberPresentInMap() throws Exception {
        Object expected = new Object();
        when(key.asNumber()).thenReturn(Optional.of(BigDecimal.ONE));
        when(map.containsKey(1)).thenReturn(true);
        when(map.get(1)).thenReturn(expected);

        Optional<Value> result = underTest.extract(map, key);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getValue(), is(expected));
    }
}