package org.jtwig.value.context;

import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class StaticVariableValueContextTest {
    public static final String STATIC_KEY = "staticKey";
    public static final Object STATIC_VALUE = new Object();
    private final ValueContext valueContext = mock(ValueContext.class);
    private StaticVariableValueContext underTest = new StaticVariableValueContext(valueContext, STATIC_KEY, STATIC_VALUE);

    @Test
    public void resolveStatic() throws Exception {
        Object result = underTest.resolve(STATIC_KEY);

        assertSame(STATIC_VALUE, result);
    }

    @Test
    public void resolveNonStatic() throws Exception {
        String key = "key";
        Object value = new Object();

        when(valueContext.resolve(key)).thenReturn(value);

        Object result = underTest.resolve(key);

        assertSame(value, result);
    }

    @Test
    public void with() throws Exception {
        String key = "key";
        Object value = new Object();

        underTest.with(key, value);

        verify(valueContext).with(key, value);
    }
}