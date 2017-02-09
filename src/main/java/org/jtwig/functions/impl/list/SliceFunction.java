package org.jtwig.functions.impl.list;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.util.FunctionValueUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SliceFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "slice";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(3).maximumNumberOfArguments(3);
        return slice(request,
                FunctionValueUtils.getNumber(request, 1).intValue(),
                FunctionValueUtils.getNumber(request, 2).intValue());
    }

    private Object slice(FunctionRequest request, int begin, int length) throws CalculationException {
        Object input = request.get(0);
        if (input instanceof String) {
            String value = (String) input;
            if (value.length() < begin) {
                return "";
            } else {
                return value.substring(begin, Math.min(value.length(), begin + length));
            }
        }

        Iterator<Object> iterator = FunctionValueUtils.getCollection(request, 0).iterator();
        List<Object> list = new ArrayList<>();
        int i = 0;
        while (iterator.hasNext()) {
            if (i >= begin && i < begin + length)
                list.add(iterator.next());
            else
                iterator.next();
            i++;
        }

        return list;
    }
}
