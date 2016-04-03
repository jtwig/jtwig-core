package org.jtwig.model.expression.test;

import org.jtwig.model.expression.Expression;

public class DivisibleByTestExpression extends TestExpression {
    private final Expression expression;

    public DivisibleByTestExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }


    //    @Override
//    public JtwigValue test(CalculateRequest request, Expression argument) {
//        MathContext mathContext = request.getEnvironment().getValueEnvironment().getMathContext();
//        BigDecimal testValue = argument.calculate(request).asNumber().or(throwException());
//        BigDecimal divisor = expression.calculate(request).asNumber().or(throwException());
//
//        boolean result = testValue.remainder(divisor, mathContext).equals(BigDecimal.ZERO);
//
//        return request.getEnvironment().getValueEnvironment().newJtwigValue(result);
//    }
//
//    private Supplier<BigDecimal> throwException() {
//        return OptionalUtils.throwException(new CalculationException(errorMessage(expression.getPosition(), "Expecting a number")));
//    }
}
