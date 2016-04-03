package org.jtwig.functions;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

import java.util.List;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class FunctionRequest extends RenderRequest {
    private final Position position;
    private final String functionName;
    private final List<Object> arguments;

    public FunctionRequest(RenderRequest request, Position position, String functionName, List<Object> arguments) {
        super(request.getRenderContext(), request.getEnvironment());
        this.position = position;
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public Position getPosition() {
        return position;
    }

    public int getNumberOfArguments () {
        return arguments.size();
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public FunctionRequest minimumNumberOfArguments(int number) {
        if (arguments.size() < number) {
            throw exception(String.format("Expected at least %d arguments", number));
        }
        return this;
    }

    public FunctionRequest maximumNumberOfArguments(int number) {
        if (arguments.size() > number) {
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
        return arguments.get(index);
    }

    public Object[] getRemainingArguments(int start) {
        if (arguments.size() <= 0) return new Object[0];
        else {
            Object[] objects = new Object[arguments.size() - start];
            for (int i = start; i < arguments.size(); i++) {
                objects[i-start] = get(i);
            }
            return objects;
        }
    }
}
