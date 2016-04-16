package org.jtwig.functions.impl.mixed;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

public class LengthFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "length";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.maximumNumberOfArguments(1).minimumNumberOfArguments(1);
        Object input = request.get(0);

        if (input == null || input == Undefined.UNDEFINED) {
            return 0;
        } else {
            Converter.Result<WrappedCollection> collectionResult = request.getEnvironment()
                    .getValueEnvironment().getCollectionConverter()
                    .convert(input);

            if (collectionResult.isDefined()) {
                return collectionResult.get().size();
            } else if (input instanceof String) {
                return ((String) input).length();
            } else {
                return 1;
            }
        }
    }
}
