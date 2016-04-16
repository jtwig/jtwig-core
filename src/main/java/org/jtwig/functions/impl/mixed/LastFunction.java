package org.jtwig.functions.impl.mixed;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

import java.util.Iterator;
import java.util.Map;

public class LastFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "last";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);

        Object input = request.get(0);
        Converter.Result<WrappedCollection> collectionResult = request.getEnvironment()
                .getValueEnvironment().getCollectionConverter()
                .convert(input);
        if (collectionResult.isDefined()) {
            Iterator<Map.Entry<String, Object>> iterator = collectionResult.get().iterator();
            if (iterator.hasNext()) return last(iterator);
            else return Undefined.UNDEFINED;
        } else if (input instanceof String) {
            String argument = (String) input;
            return argument.length() > 0 ? argument.charAt(argument.length() - 1) : Undefined.UNDEFINED;
        }

        return input;
    }

    private Object last(Iterator<Map.Entry<String, Object>> iterator) {
        Map.Entry<String, Object> last = iterator.next();
        while (iterator.hasNext()) {
            last = iterator.next();
        }
        return last.getValue();
    }
}
