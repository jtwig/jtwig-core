package org.jtwig.functions.impl.mixed;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;

import java.util.Iterator;

public class LastFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "last";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);

        Object input = request.getArgument(0, Object.class);
        if (input.getClass().isArray()) {
            Object[] array = (Object[]) input;
            return array.length == 0 ? Undefined.UNDEFINED : array[array.length - 1];
        } else if (input instanceof Iterable) {
            Iterator iterator = ((Iterable) input).iterator();
            return iterator.hasNext() ? last(iterator) : Undefined.UNDEFINED;
        } else {
            String argument = request.getArgument(0, String.class);
            return argument.charAt(argument.length() - 1);
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
