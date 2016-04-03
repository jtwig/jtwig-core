package org.jtwig.functions.impl.list;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.WrappedCollection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BatchFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "batch";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(2).maximumNumberOfArguments(3);

        int groupSize = getNumber(request, 1).intValue();

        if (request.getNumberOfArguments() == 3) {
            return batch(request, request.get(0), groupSize, request.get(2));
        } else {
            return batch(request, request.get(0), groupSize);
        }
    }

    private List<List<Object>> batch(FunctionRequest request, Object input, int groupSize) {
        Iterator<Object> iterator = getCollection(request, input).iterator();
        List<List<Object>> result = new ArrayList<>();
        while (iterator.hasNext()) {
            List<Object> batch = new ArrayList<>();
            for (int i = 0; i < groupSize; i++) {
                if (iterator.hasNext())
                    batch.add(iterator.next());
            }
            result.add(batch);
        }
        return result;
    }

    public List<List<Object>> batch(FunctionRequest request, Object input, int groupSize, Object padding) {
        Iterator<Object> iterator = getCollection(request, input).iterator();
        List<List<Object>> result = new ArrayList<>();
        while (iterator.hasNext()) {
            List<Object> batch = new ArrayList<>();
            for (int i = 0; i < groupSize; i++) {
                if (iterator.hasNext())
                    batch.add(iterator.next());
                else
                    batch.add(padding);
            }
            result.add(batch);
        }
        return result;
    }

    private Iterable<Object> getCollection(FunctionRequest request, Object input) {
        return request.getEnvironment().getValueEnvironment().getCollectionConverter()
                .convert(input).or(WrappedCollection.singleton(input))
                .values();
    }

    private BigDecimal getNumber(FunctionRequest request, int index) {
        return request.getEnvironment().getValueEnvironment().getNumberConverter().convert(request.get(index)).orThrow(request.getPosition(), String.format("Cannot convert argument %d of number_format to number", index + 1));
    }
}
