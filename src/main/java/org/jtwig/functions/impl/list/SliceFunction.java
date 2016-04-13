package org.jtwig.functions.impl.list;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.WrappedCollection;

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
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(3).maximumNumberOfArguments(3);
        return slice(request, request.get(0),
                getNumber(request, 1).intValue(),
                getNumber(request, 2).intValue());
    }


    private Object slice(FunctionRequest request, Object input, int begin, int length) throws CalculationException {
        if (input instanceof String) {
            String value = (String) input;
            if (value.length() < begin) {
                return "";
            } else {
                return value.substring(begin, Math.min(value.length(), begin + length));
            }
        }

        Iterator<Object> iterator = getCollection(request, input).iterator();
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

    private BigDecimal getNumber(FunctionRequest request, int index) {
        return request.getEnvironment().getValueEnvironment().getNumberConverter().convert(request.get(index)).orThrow(request.getPosition(), String.format("Cannot convert argument %d of number_format to number", index + 1));
    }


    private Iterable<Object> getCollection(FunctionRequest request, Object input) {
        return request.getEnvironment().getValueEnvironment().getCollectionConverter()
                .convert(input).or(WrappedCollection.singleton(input))
                .values();
    }
}
