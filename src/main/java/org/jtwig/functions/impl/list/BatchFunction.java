package org.jtwig.functions.impl.list;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.JtwigValue;

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
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(2).maximumNumberOfArguments(3);

        int groupSize = request.getArgument(1, BigDecimal.class).intValue();

        if (request.getNumberOfArguments() == 3) {
            return batch(request.get(0), groupSize, request.getArgument(2, Object.class));
        } else {
            return batch(request.get(0), groupSize);
        }
    }

    private List<List<Object>> batch(JtwigValue input, int groupSize) {
        Iterator<Object> iterator = input.asCollection().iterator();
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

    public List<List<Object>> batch(JtwigValue input, int groupSize, Object padding) {
        Iterator<Object> iterator = input.asCollection().iterator();
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
}
