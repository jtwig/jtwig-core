package org.jtwig.value.relational.greater;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StringGreaterComparatorTest {
    private StringGreaterComparator underTest = new StringGreaterComparator();

    @Test
    public void applyWhenLeftNotString() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.NUMBER);
        when(right.getType()).thenReturn(JtwigType.STRING);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void applyWhenRightNotString() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.STRING);
        when(right.getType()).thenReturn(JtwigType.NUMBER);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void applyWhenBothStringLeftHigher() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.STRING);
        when(left.asString()).thenReturn("B");
        when(right.getType()).thenReturn(JtwigType.STRING);
        when(right.asString()).thenReturn("A");

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(true));
    }

    @Test
    public void applyWhenBothStringLeftLower() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.getType()).thenReturn(JtwigType.STRING);
        when(left.asString()).thenReturn("A");
        when(right.getType()).thenReturn(JtwigType.STRING);
        when(right.asString()).thenReturn("B");

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }
}