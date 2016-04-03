package org.jtwig.render.expression.calculator.operation.binary.calculators.selection;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.model.position.Position;
import org.jtwig.property.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.RenderContext;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.jtwig.support.MatcherUtils.theSame;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

public class ResolveSelectionPropertyCalculatorTest {
    private ResolveSelectionPropertyCalculator underTest = new ResolveSelectionPropertyCalculator();

    @Test
    public void calculate() throws Exception {
        RenderRequest request = mock(RenderRequest.class);
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        RenderContext renderContext = mock(RenderContext.class);
        Position position = mock(Position.class);
        String propertyName = "propertyName";
        List<Object> arguments = asList(new Object());
        Object value = new Object();
        Optional<Value> optional = Optional.<Value>absent();

        when(request.getEnvironment()).thenReturn(environment);
        when(request.getRenderContext()).thenReturn(renderContext);
        when(environment.getPropertyResolver().resolve(argThat(theSame(new PropertyResolveRequest(
                renderContext, environment, position, new Value(value), propertyName, arguments
        ))))).thenReturn(optional);

        Optional<Value> result = underTest.calculate(request, position, propertyName, arguments, value);

        assertSame(optional, result);
    }
}