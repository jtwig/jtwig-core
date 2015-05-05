package org.jtwig.context.values;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ScopedValueContextTest {
    private final ValueContext parent = mock(ValueContext.class);
    private final ValueContext local = mock(ValueContext.class);

    private ScopedValueContext underTest = new ScopedValueContext(parent, local);

    @Test
    public void add() throws Exception {
        underTest.add("one", "two");

        verify(parent, never()).add(any(String.class), any());
        verify(local).add("one", "two");
    }

    @Test
    public void valueIfParentContains() throws Exception {
        JtwigValue jtwigValue = mock(JtwigValue.class);
        when(local.value("one")).thenReturn(Optional.<JtwigValue>absent());
        when(parent.value("one")).thenReturn(Optional.<JtwigValue>of(jtwigValue));

        Optional<JtwigValue> result = underTest.value("one");

        verify(local).value("one");
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(jtwigValue));
    }

    @Test
    public void valueIfLocalContains() throws Exception {
        JtwigValue jtwigValue = mock(JtwigValue.class);
        when(local.value("one")).thenReturn(Optional.<JtwigValue>of(jtwigValue));
        when(parent.value("one")).thenReturn(Optional.<JtwigValue>absent());

        Optional<JtwigValue> result = underTest.value("one");

        verify(parent, never()).value("one");
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(jtwigValue));
    }
}