package org.jtwig.functions.impl.mixed;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;

import java.util.Iterator;
import java.util.Map;

public class FirstFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "first";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);

        Object input = request.get(0);
        if (input.getClass().isArray()) {
            Object[] array = (Object[]) input;
            return array.length == 0 ? Undefined.UNDEFINED : array[0];
        } else if (input instanceof Iterable) {
            Iterator iterator = ((Iterable) input).iterator();
            return iterator.hasNext() ? iterator.next() : Undefined.UNDEFINED;
        } else if (input instanceof Map) {
            Iterator iterator = ((Map) input).values().iterator();
            return iterator.hasNext() ? iterator.next() : Undefined.UNDEFINED;
        } else if (input instanceof String) {
            String string = (String) input;
            return string.length() > 0 ? string.charAt(0) : "";
        } else {
            return input;
        }
    }

}
