package org.jtwig.functions.impl.list;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.JtwigType;
import org.jtwig.value.JtwigValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SliceFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "slice";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(3).maximumNumberOfArguments(3);
        return slice(request.get(0),
                request.getArgument(1, BigDecimal.class).intValue(),
                request.getArgument(2, BigDecimal.class).intValue());
    }

    private Object slice(JtwigValue input, int begin, int length) throws CalculationException {
        if (input.getType() == JtwigType.STRING) {
            String value = input.asString();
            if (value.length() < begin) return "";
            return value.substring(begin, Math.min(value.length(), begin + length));
        }

        Iterator<Object> iterator = input.asCollection().iterator();
        List list = new ArrayList();
        int i = 0;
        while (iterator.hasNext()) {
            if (i >= begin && i < begin + length)
                list.add(iterator.next());
            else
                iterator.next();
            i++;
        }

        if (input instanceof Iterable)
            return list;
        else
            return list.toArray();
    }
}
