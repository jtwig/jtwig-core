package org.jtwig.functions.convert;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ObjectToBigDecimalConverterTest {
    private ObjectToBigDecimalConverter underTest = new ObjectToBigDecimalConverter();

    @Test
    public void convertWhenNotBigDecimal() throws Exception {

        Optional<Value> result = underTest.convert("1", String.class);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void convertWhenBigDecimalButNotNumber() throws Exception {

        Optional<Value> result = underTest.convert("a", BigDecimal.class);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void convertWhenBigDecimalAndNumber() throws Exception {

        Optional<Value> result = underTest.convert("1", BigDecimal.class);

        assertThat(result.isPresent(), is(true));
    }
}