package org.jtwig.functions;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;
import org.jtwig.util.OptionalUtils;
import org.jtwig.value.JtwigValue;

import java.util.List;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class JtwigFunctionRequest {
    private final Position position;
    private final String functionName;
    private final List<JtwigValue> arguments;

    public JtwigFunctionRequest(Position position, String functionName, List<JtwigValue> arguments) {
        this.position = position;
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public int getNumberOfArguments () {
        return arguments.size();
    }

    public Object[] getArguments() {
        return arguments.toArray();
    }

    public JtwigFunctionRequest minimumNumberOfArguments(int number) {
        if (arguments.size() < number) {
            throw exception(String.format("Expected at least %d arguments", number));
        }
        return this;
    }

    public JtwigFunctionRequest maximumNumberOfArguments(int number) {
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

    public JtwigValue get(int index) {
        return arguments.get(index);
    }

    public <T> T getArgument(int index, Class<T> type) {
        return (T) arguments.get(index).as(type)
                .or(OptionalUtils.<Value, CalculationException>throwException(exception(String.format("Argument number %d cannot be converted into %s", (index + 1), type.getName()))))
                .getValue();
    }

    public Object[] getRemainingArguments(int start) {
        if (arguments.size() >= 0) return new Object[0];
        else {
            Object[] objects = new Object[arguments.size() - start];
            for (int i = start; i < arguments.size(); i++) {
                objects[i-start] = getArgument(i, Object.class);
            }
            return objects;
        }
    }
}
