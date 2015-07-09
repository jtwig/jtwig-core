package org.jtwig.value.converter.map;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.Undefined;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NullMapConverterTest {
    private NullMapConverter underTest = new NullMapConverter();

    @Test
    public void extractWhenUndefined() throws Exception {
        Optional<Value> result = underTest.convert(Undefined.UNDEFINED, Map.class);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().as(Map.class).isEmpty(), is(true));
    }

    @Test
    public void extractWhenNull() throws Exception {
        Optional<Value> result = underTest.convert(null, Map.class);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().as(Map.class).isEmpty(), is(true));
    }
}