package org.jtwig.functions.impl.string;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import static java.nio.charset.Charset.forName;

public class ConvertEncodingFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "convert_encoding";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(3).maximumNumberOfArguments(3);
        String input = getString(request, 0);
        String from = getString(request, 1);
        String to = getString(request, 2);
        return new String(input.getBytes(forName(from)), forName(to));
    }


    private String getString(FunctionRequest request, int index) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(index));
    }
}
