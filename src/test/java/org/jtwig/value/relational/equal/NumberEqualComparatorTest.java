package org.jtwig.value.relational.equal;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NumberEqualComparatorTest {
    private NumberEqualComparator underTest = new NumberEqualComparator();

    @Test
    public void applyWhenLeftNotNumber() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asNumber()).thenReturn(Optional.<BigDecimal>absent());
        when(right.asNumber()).thenReturn(Optional.of(BigDecimal.ONE));

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void applyWhenRightNotNumber() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asNumber()).thenReturn(Optional.of(BigDecimal.ONE));
        when(right.asNumber()).thenReturn(Optional.<BigDecimal>absent());

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void applyWhenBothNumberAndEqual() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asNumber()).thenReturn(Optional.of(BigDecimal.ONE));
        when(right.asNumber()).thenReturn(Optional.of(BigDecimal.ONE));

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(true));
    }

    @Test
    public void applyWhenBothNumberAndDifferent() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asNumber()).thenReturn(Optional.of(BigDecimal.ONE));
        when(right.asNumber()).thenReturn(Optional.of(BigDecimal.ZERO));

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }
}