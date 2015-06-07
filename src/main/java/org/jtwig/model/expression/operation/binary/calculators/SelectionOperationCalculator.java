package org.jtwig.model.expression.operation.binary.calculators;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.property.PropertyResolveRequestFactory;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

import java.util.Collection;
import java.util.Collections;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class SelectionOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(final RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        JtwigValue value = leftOperand.calculate(context);
        String propertyName;
        Collection<FunctionArgument> functionArguments;
        if (rightOperand instanceof VariableExpression) {
            propertyName = ((VariableExpression) rightOperand).getIdentifier();
            functionArguments = Collections.emptyList();
        } else if (rightOperand instanceof FunctionExpression) {
            propertyName = ((FunctionExpression) rightOperand).getFunctionIdentifier();
            functionArguments = ((FunctionExpression) rightOperand).getArguments().calculate(context);
        } else {
            throw new CalculationException(String.format("Expecting variable or function, but got %s", rightOperand.getClass().getSimpleName()));
        }

        return context.environment().propertyResolver()
                .resolve(PropertyResolveRequestFactory.create(position, value.asObject(), propertyName, functionArguments))
                .transform(new Function<Value, JtwigValue>() {
                    @Override
                    public JtwigValue apply(Value input) {
                        return JtwigValueFactory.value(input.getValue(), context.environment().valueConfiguration());
                    }
                })
                .or(throwUnresolvableException(context, position, propertyName, value.asObject()));
    }

    private Supplier<JtwigValue> throwUnresolvableException(final RenderContext context, final Position position, final String propertyName, final Object value) {
        return new Supplier<JtwigValue>() {
            @Override
            public JtwigValue get() {
                if (context.environment().renderConfiguration().strictMode()) {
                    throw new CalculationException(errorMessage(position, String.format("Impossible to access an attribute '%s' on '%s'", propertyName, value)));
                } else {
                    return JtwigValueFactory.undefined(context.environment().valueConfiguration());
                }
            }
        };
    }
}
