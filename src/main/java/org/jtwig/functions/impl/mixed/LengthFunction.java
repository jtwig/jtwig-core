package org.jtwig.functions.impl.mixed;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;

import java.util.Iterator;
import java.util.Map;

public class LengthFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "length";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.maximumNumberOfArguments(1).minimumNumberOfArguments(1);
        Object input = request.get(0);

        if (input == null || input == Undefined.UNDEFINED) {
            return 0;
        } else if (input.getClass().isArray()) {
            return ((Object[]) input).length;
        } else if (input instanceof Iterable) {
            return length((Iterable) input);
        } else if (input instanceof Map) {
            return length((Map) input);
        } else if (input instanceof String) {
            return ((String) input).length();
        } else {
            return 1;
        }
    }

    private int length (Iterable input) {
        Iterator iterator = input.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    private int length (Map input) {
        return input.size();
    }
}
