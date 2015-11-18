package org.jtwig.functions.impl.control;

import org.jtwig.context.RenderContext;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.impl.AbstractFunctionTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EscapeFunctionTest extends AbstractFunctionTest {
    private final RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private EscapeFunction underTest = new EscapeFunction() {
        @Override
        protected RenderContext getRenderContext() {
            return renderContext;
        }
    };

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void executeLessThanOneArgument() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Expected at least 1 arguments"));

        underTest.execute(withoutArguments());
    }

    @Test
    public void executeMoreThan2Arguments() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Expected at most 2 arguments"));

        underTest.execute(arguments(newValue(), newValue(), newValue()));
    }

    @Test
    public void defaultEscapeMode() throws Exception {
        Object result = underTest.execute(arguments(newValue("value")));

        verify(renderContext.currentNode()).mode(EscapeMode.HTML);
        assertEquals("value", result);
    }

    @Test
    public void noneEscapeMode() throws Exception {
        Object result = underTest.execute(arguments(newValue("value"), newValue("none")));

        verify(renderContext.currentNode()).mode(EscapeMode.NONE);
        assertEquals("value", result);
    }

    @Test
    public void falseEscapeMode() throws Exception {
        Object result = underTest.execute(arguments(realValue("value"), realValue(false)));

        verify(renderContext.currentNode()).mode(EscapeMode.NONE);
        assertEquals("value", result);
    }

    @Test
    public void jsEscapeMode() throws Exception {
        Object result = underTest.execute(arguments(newValue("value"), newValue("js")));

        verify(renderContext.currentNode()).mode(EscapeMode.JS);
        assertEquals("value", result);
    }

    @Test
    public void javascriptEscapeMode() throws Exception {
        Object result = underTest.execute(arguments(newValue("value"), newValue("javascript")));

        verify(renderContext.currentNode()).mode(EscapeMode.JAVASCRIPT);
        assertEquals("value", result);
    }

    @Test
    public void htmlEscapeMode() throws Exception {
        Object result = underTest.execute(arguments(newValue("value"), newValue("html")));

        verify(renderContext.currentNode()).mode(EscapeMode.HTML);
        assertEquals("value", result);
    }

    @Test
    public void unknownEscapeMode() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Invalid escape mode requested 'blah'. Only supporting [NONE, HTML, JS, JAVASCRIPT]"));

        Object result = underTest.execute(arguments(newValue("value"), newValue("blah")));
    }
}