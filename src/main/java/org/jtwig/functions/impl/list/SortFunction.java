package org.jtwig.functions.impl.list;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "sort";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.maximumNumberOfArguments(1).minimumNumberOfArguments(1);
        ArrayList<Object> result = new ArrayList<>(request.get(0).asCollection());
        if (result.isEmpty() || result.size() == 1) {
            return result;
        } else {
            if (result.get(0) instanceof Comparable) {
                List<Comparable> comparables = (List) result;
                Collections.sort(comparables);
            } else {
                throw request.exception("Cannot sort list of uncomparable items");
            }
        }
        return result;
    }
}
