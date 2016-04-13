package org.jtwig.functions.impl.list;

import org.apache.commons.lang3.StringUtils;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JoinFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "join";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(2);
        String separator = "";
        if (request.getNumberOfArguments() == 2) {
            separator = getString(request, 1);
        }

        return join(request, request.get(0), separator);
    }

    private String getString(FunctionRequest request, int index) {
        return getString(request, request.get(index));
    }

    private String getString(FunctionRequest request, Object value) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(value);
    }

    private String join(FunctionRequest request, Object input, String separator) {
        Converter<WrappedCollection> collectionConverter = request.getEnvironment().getValueEnvironment().getCollectionConverter();
        List<String> pieces = new ArrayList<>();

        WrappedCollection objects = collectionConverter.convert(input).or(WrappedCollection.singleton(input));
        for (Map.Entry<String, Object> entry : objects) {
            if (entry.getValue() != null && entry.getValue() != Undefined.UNDEFINED) {
                pieces.add(getString(request, entry.getValue()));
            }
        }
        return StringUtils.join(pieces, separator);
    }
}
