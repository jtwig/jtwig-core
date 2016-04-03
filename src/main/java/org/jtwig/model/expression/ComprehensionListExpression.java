package org.jtwig.model.expression;

import org.jtwig.model.position.Position;

public class ComprehensionListExpression extends Expression {
    private final Expression start;
    private final Expression end;

    public ComprehensionListExpression(Position position, Expression start, Expression end) {
        super(position);
        this.start = start;
        this.end = end;
    }

    public Expression getStart() {
        return start;
    }

    public Expression getEnd() {
        return end;
    }

    //    @Override
//    public JtwigValue calculate(final CalculateRequest request) {
//        final JtwigValue startValue = start.calculate(request);
//        final JtwigValue endValue = end.calculate(request);
//        return request.getEnvironment().getListEnumerationStrategy().enumerate(startValue, endValue)
//                .transform(new Function<Collection<Object>, JtwigValue>() {
//                    @Override
//                    public JtwigValue apply(Collection<Object> input) {
//                        return request.getEnvironment().getValueEnvironment().newJtwigValue(input);
//                    }
//                })
//                .or(new Supplier<JtwigValue>() {
//                    @Override
//                    public JtwigValue get() {
//                        throw new CalculationException(errorMessage(getPosition(), String.format("Unable to calculate a list from a comprehension list starting with '%s' and ending with '%s'", startValue.asObject(), endValue.asObject())));
//                    }
//                });
//    }
}
