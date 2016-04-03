package org.jtwig.functions.impl.map;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.WrappedCollection;

public class KeysFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "keys";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.maximumNumberOfArguments(1).minimumNumberOfArguments(1);

        WrappedCollection wrappedCollection = request.getEnvironment().getValueEnvironment().getCollectionConverter().convert(request.get(0))
                .or(WrappedCollection.empty());

        return wrappedCollection.keys();
    }
}
