package org.jtwig.functions.impl.mixed;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class ReverseFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "reverse";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);

        Object input = request.get(0);
        if (input.getClass().isArray()) {
            return reverseArray((Object[]) input);
        } else if (input instanceof Iterable) {
            Iterator iterator = ((Iterable) input).iterator();
            ArrayList<Object> list = new ArrayList<>();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
            return Lists.reverse(list);
        } else if (input instanceof String) {
            return StringUtils.reverse((String) input);
        } else {
            return input;
        }
    }

    private Object reverseArray(Object[] input) {
        Object[] array = input;
        Object[] newInstance = (Object[]) Array.newInstance(input.getClass().getComponentType(), array.length);
        for (int i = 0; i < newInstance.length / 2; i++) {
            newInstance[i] = array[newInstance.length - i - 1];
            newInstance[newInstance.length - i - 1] = array[i];
        }
        return newInstance;
    }
}
