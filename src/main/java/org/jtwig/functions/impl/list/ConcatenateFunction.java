package org.jtwig.functions.impl.list;

import org.jtwig.functions.JtwigFunction;
import org.jtwig.functions.JtwigFunctionRequest;

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
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1);
        StringBuilder builder = new StringBuilder();
        for (Object piece : request.getRemainingArguments(0)) {
            if (piece != null)
                builder.append(piece);

        }
        return builder.toString();
    }
}
