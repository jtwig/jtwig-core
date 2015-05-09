package org.jtwig.value.relational;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotComparatorTest {
    private final RelationalComparator relationalComparator = mock(RelationalComparator.class);
    private NotComparator notComparator = new NotComparator(relationalComparator);

    @Test
    public void notWhenDelegateReturnsAbsent() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(relationalComparator.apply(left, right)).thenReturn(Optional.<Boolean>absent());

        Optional<Boolean> result = notComparator.apply(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void notWhenDelegateReturnsTrue() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(relationalComparator.apply(left, right)).thenReturn(Optional.of(true));

        Optional<Boolean> result = notComparator.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(false));
    }

    @Test
    public void notWhenDelegateReturnsFalse() throws Exception {
        JtwigValue left = mock(JtwigValue.class);
        JtwigValue right = mock(JtwigValue.class);
        when(relationalComparator.apply(left, right)).thenReturn(Optional.of(false));

        Optional<Boolean> result = notComparator.apply(left, right);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(true));
    }
}