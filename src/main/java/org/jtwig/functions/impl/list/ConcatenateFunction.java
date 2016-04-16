package org.jtwig.functions.impl.list;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.JtwigFunction;

import java.util.Collection;
import java.util.Collections;

public class ConcatenateFunction implements JtwigFunction {
    @Override
    public String name() {
        return "concat";
    }

    @Override
    public Collection<String> aliases() {
        return Collections.singleton("concatenate");
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1);
        StringBuilder builder = new StringBuilder();
        for (Object piece : request.getRemainingArguments(0)) {
            builder.append(request.getEnvironment().getValueEnvironment().getStringConverter().convert(piece));
        }
        return builder.toString();
    }
}
