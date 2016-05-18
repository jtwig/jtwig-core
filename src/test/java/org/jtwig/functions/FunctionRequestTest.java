package org.jtwig.functions;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FunctionRequestTest {
    public static final String FUNCTION_NAME = "functionName";
    private final FunctionArguments functionArguments = mock(FunctionArguments.class);
    private FunctionRequest underTest = new FunctionRequest(mock(RenderRequest.class), mock(Position.class), FUNCTION_NAME, functionArguments);

    @Test
    public void exceptionWithArgument() throws Exception {
        RuntimeException exception = new RuntimeException();
        String message = "asd";

        CalculationException result = underTest.exception(message, exception);

        assertSame(result.getCause(), exception);
        assertThat(result.getMessage(), containsString(message));
    }

    @Test
    public void getExpressionTest() throws Exception {
        Expression expression = mock(Expression.class);
        when(functionArguments.getExpression(0)).thenReturn(expression);

        Expression result = underTest.getExpression(0);

        assertSame(expression, result);
    }

    @Test
    public void getExpressionsTest() throws Exception {
        Expression expression = mock(Expression.class);
        List<Expression> expressions = Collections.singletonList(expression);
        when(functionArguments.getExpressions()).thenReturn(expressions);

        List<Expression> result = underTest.getExpressionArguments();

        assertSame(expressions, result);
    }
}