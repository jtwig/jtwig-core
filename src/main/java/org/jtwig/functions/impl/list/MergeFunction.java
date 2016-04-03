package org.jtwig.functions.impl.list;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.WrappedCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MergeFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "merge";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(2);
        Collection<Object> result = new ArrayList<>();
        List<Object> arguments = request.getArguments();

        for (Object argument : arguments) {
            WrappedCollection collection = getCollection(request, argument);
            result.addAll(collection.values());
        }

        return result;
    }

    private WrappedCollection getCollection(FunctionRequest request, Object value) {
        return request.getEnvironment().getValueEnvironment().getCollectionConverter().convert(value).or(WrappedCollection.singleton(value));
    }
}
