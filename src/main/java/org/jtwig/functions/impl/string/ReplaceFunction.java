package org.jtwig.functions.impl.string;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.WrappedCollection;

import java.util.Iterator;
import java.util.Map;

public class ReplaceFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "replace";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(2).maximumNumberOfArguments(2);
        String input = getString(request, request.get(0));

        WrappedCollection wrappedCollection = request.getEnvironment().getValueEnvironment()
                .getCollectionConverter().convert(request.get(1))
                .or(WrappedCollection.empty());

        Iterator<Map.Entry<String, Object>> iterator = wrappedCollection.iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            input = input.replace(entry.getKey(), getString(request, entry.getValue()));
        }
        return input;
    }

    private String getString(FunctionRequest request, Object value) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(value);
    }
}
