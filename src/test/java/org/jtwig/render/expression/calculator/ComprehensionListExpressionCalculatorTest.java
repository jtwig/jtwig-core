package org.jtwig.render.expression.calculator;

import com.google.common.base.Optional;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.ComprehensionListExpression;
import org.jtwig.model.expression.Expression;
import org.jtwig.render.RenderRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class ComprehensionListExpressionCalculatorTest {
    private ComprehensionListExpressionCalculator underTest = new ComprehensionListExpressionCalculator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateWhenNoStrategy() throws Exception {
        Object startValue = "blah";
        Object endValue = "bluh";
        Expression end = mock(Expression.class);
        Expression start = mock(Expression.class);
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        ComprehensionListExpression comprehensionListExpression = mock(ComprehensionListExpression.class);

        when(comprehensionListExpression.getStart()).thenReturn(start);
        when(comprehensionListExpression.getEnd()).thenReturn(end);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, start)).thenReturn(startValue);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, end)).thenReturn(endValue);
        when(request.getEnvironment().getListEnumerationStrategy().enumerate(request, startValue, endValue)).thenReturn(Optional.<List<Object>>absent());

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Unable to calculate a list from a comprehension list starting with 'blah' and ending with 'bluh'"));

        underTest.calculate(request, comprehensionListExpression);
    }

    @Test
    public void calculateWhenSomeStrategy() throws Exception {
        Object startValue = "blah";
        Object endValue = "bluh";
        Expression end = mock(Expression.class);
        Expression start = mock(Expression.class);
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        ComprehensionListExpression comprehensionListExpression = mock(ComprehensionListExpression.class);
        List<Object> expected = Collections.singletonList(new Object());

        when(comprehensionListExpression.getStart()).thenReturn(start);
        when(comprehensionListExpression.getEnd()).thenReturn(end);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, start)).thenReturn(startValue);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, end)).thenReturn(endValue);
        when(request.getEnvironment().getListEnumerationStrategy().enumerate(request, startValue, endValue)).thenReturn(Optional.of(expected));

        Object result = underTest.calculate(request, comprehensionListExpression);

        assertSame(expected, result);
    }
}