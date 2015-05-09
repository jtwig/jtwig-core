package org.jtwig.value.relational.greater;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NumberGreaterComparatorTest {
    private NumberGreaterComparator underTest = new NumberGreaterComparator();

    @Test
    public void applyWhenLeftNonNumber() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asNumber()).thenReturn(Optional.<BigDecimal>absent());
        when(right.asNumber()).thenReturn(Optional.of(BigDecimal.ONE));

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void applyWhenRightNonNumber() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asNumber()).thenReturn(Optional.of(BigDecimal.ONE));
        when(right.asNumber()).thenReturn(Optional.<BigDecimal>absent());

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void applyWhenLeftAndRightNumbersLeftHigher() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asNumber()).thenReturn(Optional.of(BigDecimal.ONE));
        when(right.asNumber()).thenReturn(Optional.of(BigDecimal.TEN));

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }

    @Test
    public void applyWhenLeftAndRightNumbersLeftLower() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asNumber()).thenReturn(Optional.of(BigDecimal.TEN));
        when(right.asNumber()).thenReturn(Optional.of(BigDecimal.ONE));

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(true));
    }
}