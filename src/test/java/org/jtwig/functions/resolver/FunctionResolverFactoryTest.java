package org.jtwig.functions.resolver;

import org.jtwig.exceptions.InvalidFunctionNameException;
import org.jtwig.functions.JtwigFunction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FunctionResolverFactoryTest {
    private FunctionResolverFactory underTest = new FunctionResolverFactory();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void create() throws Exception {
        JtwigFunction jtwigFunction = mock(JtwigFunction.class);

        when(jtwigFunction.name()).thenReturn("~");

        expectedException.expect(InvalidFunctionNameException.class);
        expectedException.expectMessage(containsString(String.format("Function name %s is invalid, it should be an identifier (regular expression: [A-Za-z_$][A-Za-z0-9_$]*)", "~")));

        underTest.create(new FunctionResolverConfiguration(Collections.singleton(jtwigFunction)));
    }
}