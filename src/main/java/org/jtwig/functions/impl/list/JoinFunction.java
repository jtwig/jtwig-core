package org.jtwig.functions.impl.list;

import org.apache.commons.lang3.StringUtils;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.JtwigValue;

import java.util.ArrayList;
import java.util.List;

public class JoinFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "join";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(2);
        String separator = "";
        if (request.getNumberOfArguments() == 2) {
            separator = request.getArgument(1, String.class);
        }

        return join(request.get(0), separator);
    }

    private String join (JtwigValue input, String separator) {
        List<String> pieces = new ArrayList<>();
        for (Object next : input.asCollection()) {
            if (next == null) pieces.add("");
            else pieces.add(next.toString());
        }
        return StringUtils.join(pieces, separator);
    }
}
