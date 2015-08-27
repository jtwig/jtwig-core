package org.jtwig.value.relational.lower;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComparableLowerComparatorTest {
    private ComparableLowerComparator underTest = new ComparableLowerComparator();

    @Test
    public void compareWhenLeftNotComparable() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asObject()).thenReturn(new Object());
        when(right.asObject()).thenReturn(1);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void compareWhenRightNotComparable() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asObject()).thenReturn(2);
        when(right.asObject()).thenReturn(new Object());

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void compareWhenBothComparable() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asObject()).thenReturn(2);
        when(right.asObject()).thenReturn(1);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }
}