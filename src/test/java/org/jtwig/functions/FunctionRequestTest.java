package org.jtwig.functions;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class FunctionRequestTest {
    public static final String FUNCTION_NAME = "functionName";
    private FunctionRequest underTest = new FunctionRequest(mock(RenderRequest.class), mock(Position.class), FUNCTION_NAME, new ArrayList<>());

    @Test
    public void exceptionWithArgument() throws Exception {
        RuntimeException exception = new RuntimeException();
        String message = "asd";

        CalculationException result = underTest.exception(message, exception);

        assertSame(result.getCause(), exception);
        assertThat(result.getMessage(), containsString(message));
    }

    @Test
    public void remainingArguments() throws Exception {
        Object[] result = underTest.getRemainingArguments(0);

        assertArrayEquals(result, new Object[]{});
    }
}