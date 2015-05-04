package org.jtwig.functions.convert;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ObjectToIntegerConverterTest {
    private ObjectToIntegerConverter underTest = new ObjectToIntegerConverter();

    @Test
    public void convertWhenDestinationNotInteger() throws Exception {
        Optional<Value> result = underTest.convert("1", String.class);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void convertWhenDestinationInteger() throws Exception {
        Optional<Value> result = underTest.convert("1", Integer.class);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getValue(), is((Object)1));
    }

    @Test
    public void convertWhenDestinationIntegerInconvertible() throws Exception {
        Optional<Value> result = underTest.convert("a", Integer.class);

        assertThat(result.isPresent(), is(false));
    }
}