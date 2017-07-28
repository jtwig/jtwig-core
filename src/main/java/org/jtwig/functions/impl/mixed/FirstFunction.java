package org.jtwig.functions.impl.mixed;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

import java.util.Iterator;
import java.util.Map;

public class FirstFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "first";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);

        Object input = request.get(0);
        if (input == null) {
            throw request.exception("Cannot get first element from a collection: input is null.");
        }
        Converter.Result<WrappedCollection> collectionResult = request.getEnvironment()
                .getValueEnvironment().getCollectionConverter()
                .convert(input);

        if (collectionResult.isDefined()) {
            Iterator<Map.Entry<String, Object>> iterator = collectionResult.get().iterator();
            if (iterator.hasNext()) return iterator.next().getValue();
            else return Undefined.UNDEFINED;
        } else if (input instanceof String) {
            String string = (String) input;
            return string.length() > 0 ? string.charAt(0) : Undefined.UNDEFINED;
        }

        return input;
    }

}
