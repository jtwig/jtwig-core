package org.jtwig.functions.impl.mixed;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.util.Iterator;
import java.util.Map;

public class LengthFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "length";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.maximumNumberOfArguments(1).minimumNumberOfArguments(1);
        Object input = request.getArgument(0, Object.class);

        if (input.getClass().isArray()) {
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
