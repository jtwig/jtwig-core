package org.jtwig.functions.impl.mixed;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;

import java.util.Iterator;
import java.util.Map;

public class LastFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "last";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);

        Object input = request.get(0);
        if (input.getClass().isArray()) {
            Object[] array = (Object[]) input;
            return array.length == 0 ? Undefined.UNDEFINED : array[array.length - 1];
        } else if (input instanceof Iterable) {
            Iterator iterator = ((Iterable) input).iterator();
            return iterator.hasNext() ? last(iterator) : Undefined.UNDEFINED;
        } else if (input instanceof Map) {
            Iterator iterator = ((Map) input).values().iterator();
            return iterator.hasNext() ? last(iterator) : Undefined.UNDEFINED;
        } else if (input instanceof String) {
            String argument = (String) input;
            return argument.length() > 0 ? argument.charAt(argument.length() - 1) : Undefined.UNDEFINED;
        } else {
            return input;
        }
    }

    private Object last(Iterator iterator) {
        Object last = iterator.next();
        while (iterator.hasNext()) {
            last = iterator.next();
        }
        return last;
    }
}
