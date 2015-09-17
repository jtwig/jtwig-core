package org.jtwig.functions.impl.list;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.util.*;

public class MergeFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "merge";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(2);
        Object first = request.getArgument(0, Object.class);
        Object[] remainingArguments = request.getRemainingArguments(1);
        if (first instanceof Iterable) {
            return mergeList(first, remainingArguments);
        } else if (first instanceof Map)
            return mergeMap(first, remainingArguments);
        else // is array (precondition)
            return mergeArray(first, remainingArguments);
    }


    private Object mergeArray(Object first, Object[] arguments) {
        List<Object> result = new ArrayList<>();
        for (Object obj : (Object[]) first)
            result.add(obj);
        for (Object obj : arguments) {
            if (obj == null) continue;
            Object[] list = (Object[]) obj;
            for (Object value : list) {
                result.add(value);
            }
        }
        return result.toArray();
    }

    private Object mergeMap(Object first, Object[] arguments) {
        Map<Object, Object> result;
        if (first instanceof TreeMap)
            result = new TreeMap<Object, Object>();
        else
            result = new HashMap<Object, Object>();
        result.putAll((Map) first);
        for (Object obj : arguments) {
            if (obj == null) continue;
            result.putAll((Map) obj);
        }
        return result;
    }

    private Object mergeList(Object first, Object[] arguments) {
        List<Object> result = new ArrayList<Object>();
        result.addAll((List) first);
        for (Object obj : arguments) {
            if (obj == null) continue;
            result.addAll((List) obj);
        }
        return result;
    }
}
