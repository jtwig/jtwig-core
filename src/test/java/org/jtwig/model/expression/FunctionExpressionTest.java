package org.jtwig.model.expression;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.function.Argument;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.configuration.BooleanConfigurationParameters.STRICT_MODE;
import static org.mockito.Mockito.*;

public class FunctionExpressionTest {
    private final Position position = mock(Position.class);
    private final String functionIdentifier = "identifier";
    private final ArrayList<Argument> list = new ArrayList<>();
    private FunctionExpression underTest = new FunctionExpression(position, functionIdentifier, list);
    private RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);

    @Test
    public void calculateWhenFunctionExists() throws Exception {
        Object expectedResult = new Object();
        Supplier executable = mock(Supplier.class);

        when(executable.get()).thenReturn(expectedResult);
        when(context.configuration().functionResolver().resolve(eq(functionIdentifier), anyList())).thenReturn(Optional.of(executable));

        JtwigValue jtwigValue = underTest.calculate(context);

        assertThat(jtwigValue.asObject(), is(expectedResult));
    }

    @Test(expected = CalculationException.class)
    public void calculateWhenFunctionDoesNotExist() throws Exception {

        when(context.configuration().functionResolver().resolve(eq(functionIdentifier), anyList())).thenReturn(Optional.<Supplier>absent());

        underTest.calculate(context);
    }
}