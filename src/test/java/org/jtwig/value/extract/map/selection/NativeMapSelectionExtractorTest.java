package org.jtwig.value.extract.map.selection;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NativeMapSelectionExtractorTest {
    private final Map map = mock(Map.class);
    private final JtwigValue key = mock(JtwigValue.class);
    private NativeMapSelectionExtractor underTest = new NativeMapSelectionExtractor();

    @Test
    public void resolveWhenMapNotContains() throws Exception {
        Object value = new Object();
        when(key.asObject()).thenReturn(value);
        when(map.containsKey(value)).thenReturn(false);

        Optional<Value> result = underTest.extract(map, key);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenMapContains() throws Exception {
        Object value = new Object();
        Object expected = new Object();
        when(key.asObject()).thenReturn(value);
        when(map.containsKey(value)).thenReturn(true);
        when(map.get(value)).thenReturn(expected);

        Optional<Value> result = underTest.extract(map, key);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getValue(), is(expected));
    }
}