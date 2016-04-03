package org.jtwig.value.context;

import org.jtwig.value.Undefined;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class IsolateParentValueContextTest {
    private final ValueContext parent = mock(ValueContext.class);
    private final ValueContext current = mock(ValueContext.class);
    private IsolateParentValueContext underTest = new IsolateParentValueContext(parent, current);

    @Test
    public void resolveCurrentNull() throws Exception {
        String key = "key";
        Object value = null;

        when(current.resolve(key)).thenReturn(value);

        Object result = underTest.resolve(key);

        verifyZeroInteractions(parent);
        assertSame(value, result);
    }

    @Test
    public void resolveCurrentNotNull() throws Exception {
        String key = "key";
        Object value = new Object();

        when(current.resolve(key)).thenReturn(value);

        Object result = underTest.resolve(key);

        verifyZeroInteractions(parent);
        assertSame(value, result);
    }

    @Test
    public void resolveCurrentUndefined() throws Exception {
        String key = "key";
        Object value = Undefined.UNDEFINED;
        Object expected = new Object();

        when(current.resolve(key)).thenReturn(value);
        when(parent.resolve(key)).thenReturn(expected);

        Object result = underTest.resolve(key);

        assertSame(expected, result);
    }

    @Test
    public void with() throws Exception {
        Object value = new Object();
        String key = "key";

        underTest.with(key, value);

        verify(current).with(key, value);
        verifyZeroInteractions(parent);
    }
}