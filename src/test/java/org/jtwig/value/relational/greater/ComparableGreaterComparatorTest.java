package org.jtwig.value.relational.greater;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComparableGreaterComparatorTest {
    private ComparableGreaterComparator underTest = new ComparableGreaterComparator();

    @Test
    public void applyWhenLeftNonComparable() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asObject()).thenReturn(new Object());
        when(right.asObject()).thenReturn(1);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }
    @Test
    public void applyWhenRightNonComparable() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asObject()).thenReturn(1);
        when(right.asObject()).thenReturn(new Object());

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }
    @Test
    public void applyWhenLeftAndRightComparableLeftHigher() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asObject()).thenReturn(1);
        when(right.asObject()).thenReturn(2);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }

    @Test
    public void applyWhenLeftAndRightComparableLeftLower() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(left.asObject()).thenReturn(3);
        when(right.asObject()).thenReturn(2);

        Optional<Boolean> result = underTest.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(true));
    }
}