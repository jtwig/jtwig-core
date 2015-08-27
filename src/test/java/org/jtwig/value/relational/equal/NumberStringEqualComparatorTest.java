package org.jtwig.value.relational.equal;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NumberStringEqualComparatorTest {
    private NumberStringEqualComparator underTest = new NumberStringEqualComparator();

    @Test
    public void applyWhenLeftNumberAndRightNumber() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.NUMBER);
        when(right.getType()).thenReturn(JtwigType.NUMBER);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void applyWhenLeftNumberAndRightStringAndNumberFormat() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.NUMBER);
        when(right.getType()).thenReturn(JtwigType.STRING);
        when(right.isStringNumber()).thenReturn(true);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void applyWhenLeftNumberAndRightStringAndNotNumberFormat() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.NUMBER);
        when(right.getType()).thenReturn(JtwigType.STRING);
        when(right.isStringNumber()).thenReturn(false);

        when(left.mandatoryNumber()).thenReturn(BigDecimal.TEN);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }

    @Test
    public void applyWhenLeftNumberAndRightStringAndNotNumberFormatZero() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.NUMBER);
        when(right.getType()).thenReturn(JtwigType.STRING);
        when(right.isStringNumber()).thenReturn(false);

        when(left.mandatoryNumber()).thenReturn(BigDecimal.ZERO);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(true));
    }

    //
    @Test
    public void applyWhenLeftSomething() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.BOOLEAN);
        when(left.isStringNumber()).thenReturn(true);
        when(right.getType()).thenReturn(JtwigType.NUMBER);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void applyWhenLeftStringAndNotNumberFormatAndRightNotNumber() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.STRING);
        when(left.isStringNumber()).thenReturn(false);
        when(right.getType()).thenReturn(JtwigType.ANY);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void applyWhenRightNumberAndLeftStringAndNumberFormat() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.STRING);
        when(left.isStringNumber()).thenReturn(true);
        when(right.getType()).thenReturn(JtwigType.NUMBER);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void applyWhenRightNumberAndLeftStringAndNotNumberFormat() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.STRING);
        when(left.isStringNumber()).thenReturn(false);
        when(right.getType()).thenReturn(JtwigType.NUMBER);

        when(right.mandatoryNumber()).thenReturn(BigDecimal.TEN);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }

    @Test
    public void applyWhenRightNumberAndLeftStringAndNotNumberFormatZero() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.STRING);
        when(left.isStringNumber()).thenReturn(false);
        when(right.getType()).thenReturn(JtwigType.NUMBER);

        when(right.mandatoryNumber()).thenReturn(BigDecimal.ZERO);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(true));
    }
}