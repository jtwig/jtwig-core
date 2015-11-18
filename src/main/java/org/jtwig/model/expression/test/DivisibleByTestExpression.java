package org.jtwig.model.expression.test;

import com.google.common.base.Function;
import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.util.OptionalUtils;
import org.jtwig.value.JtwigValue;

import java.math.BigDecimal;

public class DivisibleByTestExpression extends TestExpression {
    private final Expression expression;

    public DivisibleByTestExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public JtwigValue test(final RenderContext context, final Expression argument) {
        return argument.calculate(context).map(new Function<JtwigValue, Object>() {
            @Override
            public Object apply(JtwigValue input) {
                BigDecimal divisor = expression.calculate(context).asNumber()
                        .or(OptionalUtils.<BigDecimal, CalculationException>throwException(new CalculationException(ErrorMessageFormatter.errorMessage(expression.getPosition(), String.format("Expecting a number")))));
                return input.asNumber().or(OptionalUtils.<BigDecimal, CalculationException>throwException(new CalculationException(ErrorMessageFormatter.errorMessage(expression.getPosition(), String.format("Expecting a number")))))
                        .remainder(divisor, context.environment()
                                .value().getMathContext())
                        .equals(BigDecimal.ZERO);
            }
        });
    }
}
