package org.jtwig.functions.impl.list;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.util.FunctionValueUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.jtwig.util.FunctionValueUtils.getNumber;

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
            return batch(request, 0, groupSize, request.get(2));
        } else {
            return batch(request, 0, groupSize);
        }
    }

    private List<List<Object>> batch(FunctionRequest request, int index, int groupSize) {
        Iterator<Object> iterator = FunctionValueUtils.getCollection(request, index).iterator();
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

    public List<List<Object>> batch(FunctionRequest request, int index, int groupSize, Object padding) {
        Iterator<Object> iterator = FunctionValueUtils.getCollection(request, index).iterator();
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
