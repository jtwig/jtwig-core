package org.jtwig.functions.impl.mixed;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;

import java.util.Iterator;

public class FirstFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "first";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);

        Object input = request.getArgument(0, Object.class);
        if (input.getClass().isArray()) {
            Object[] array = (Object[]) input;
            return array.length == 0 ? Undefined.UNDEFINED : array[0];
        } else if (input instanceof Iterable) {
            Iterator iterator = ((Iterable) input).iterator();
            return iterator.hasNext() ? iterator.next() : Undefined.UNDEFINED;
        } else {
            return request.getArgument(0, String.class).charAt(0);
        }
    }
}
