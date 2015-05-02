package org.jtwig.functions.resolver;

import org.jtwig.functions.FunctionArgument;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FunctionArgumentValueResolverTest {
    private FunctionArgumentValueResolver underTest = new FunctionArgumentValueResolver();

    @Test
    public void resolve() throws Exception {
        Object value = new Object();
        FunctionArgument functionArgument = mock(FunctionArgument.class, RETURNS_DEEP_STUBS);
        when(functionArgument.getValue().asObject()).thenReturn(value);

        Object result = underTest.resolve(functionArgument);

        assertSame(value, result);
    }
}