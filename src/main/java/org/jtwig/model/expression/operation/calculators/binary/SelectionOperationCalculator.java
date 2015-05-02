package org.jtwig.model.expression.operation.calculators.binary;

import com.google.common.base.Supplier;
import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.property.PropertyResolveRequest;
import org.jtwig.util.JtwigValue;

import java.util.Collection;
import java.util.Collections;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class SelectionOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
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

        return context.configuration().propertyResolver()
                .resolve(new PropertyResolveRequest(position, value.asObject(), propertyName, functionArguments))
                .or(throwUnresolvableException(position, propertyName));
    }

    private Supplier<? extends JtwigValue> throwUnresolvableException(final Position position, final String propertyName) {
        return new Supplier<JtwigValue>() {
            @Override
            public JtwigValue get() {
                throw new CalculationException(errorMessage(position, String.format("Cannot resolve property '%s'", propertyName)));
            }
        };
    }
}
