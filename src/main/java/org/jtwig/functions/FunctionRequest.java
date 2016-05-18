package org.jtwig.functions;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

import java.util.List;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class FunctionRequest extends RenderRequest {
    private final Position position;
    private final String functionName;
    private final FunctionArguments functionArguments;

    public FunctionRequest(RenderRequest request, Position position, String functionName, FunctionArguments functionArguments) {
        super(request.getRenderContext(), request.getEnvironment());
        this.position = position;
        this.functionName = functionName;
        this.functionArguments = functionArguments;
    }

    public Position getPosition() {
        return position;
    }

    public int getNumberOfArguments () {
        return functionArguments.size();
    }

    public List<Object> getArguments() {
        return functionArguments.getValues();
    }

    public List<Expression> getExpressionArguments () {
        return functionArguments.getExpressions();
    }

    public FunctionRequest minimumNumberOfArguments(int number) {
        if (functionArguments.size() < number) {
            throw exception(String.format("Expected at least %d arguments", number));
        }
        return this;
    }

    public FunctionRequest maximumNumberOfArguments(int number) {
        if (functionArguments.size() > number) {
            throw exception(String.format("Expected at most %d arguments", number));
        }
        return this;
    }

    public CalculationException exception(String message) {
        return new CalculationException(errorMessage(position, String.format("Function %s error: %s", functionName, message)));
    }

    public CalculationException exception(String message, Throwable e) {
        return new CalculationException(errorMessage(position, String.format("Function %s error: %s", functionName, message)), e);
    }

    public Object get(int index) {
        return functionArguments.getValue(index);
    }

    public Expression getExpression (int index) { return functionArguments.getExpression(index); }

    public Object[] getRemainingArguments(int start) {
        return functionArguments.getRemainingArguments(start);
    }
}
