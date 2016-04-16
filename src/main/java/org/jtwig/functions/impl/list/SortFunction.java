package org.jtwig.functions.impl.list;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "sort";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.maximumNumberOfArguments(1).minimumNumberOfArguments(1);
        Converter<WrappedCollection> collectionConverter = request.getEnvironment().getValueEnvironment().getCollectionConverter();

        Object object = request.get(0);
        List<Object> result = new ArrayList<>(collectionConverter.convert(object)
                .or(WrappedCollection.singleton(object))
                .values());


        if (result.isEmpty() || result.size() == 1) {
            return result;
        } else {
            if (result.get(0) instanceof Comparable) {
                Collections.sort((List) result);
            } else {
                throw request.exception(String.format("Sort function only works over collections of items, where items implement the %s interface", Comparable.class));
            }
        }

        return result;
    }
}
