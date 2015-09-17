package org.jtwig.functions.impl.string;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import static java.nio.charset.Charset.forName;

public class ConvertEncodingFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "convert_encoding";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(3).maximumNumberOfArguments(3);
        String input = request.getArgument(0, String.class);
        String from = request.getArgument(1, String.class);
        String to = request.getArgument(2, String.class);
        return new String(input.getBytes(forName(from)), forName(to));
    }
}
