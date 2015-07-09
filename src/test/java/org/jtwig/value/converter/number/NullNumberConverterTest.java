package org.jtwig.value.converter.number;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.Undefined;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class NullNumberConverterTest {
    private NullNumberConverter underTest = new NullNumberConverter();

    @Test
    public void extractWhenUndefined() throws Exception {
        Optional<Value> result = underTest.convert(Undefined.UNDEFINED, BigDecimal.class);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().as(BigDecimal.class), is(BigDecimal.ZERO));
    }

    @Test
    public void extractWhenNull() throws Exception {
        Optional<Value> result = underTest.convert(null, BigDecimal.class);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().as(BigDecimal.class), is(BigDecimal.ZERO));
    }
}