package org.jtwig.functions.impl.map;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.util.Map;

public class KeysFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "keys";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.maximumNumberOfArguments(1).minimumNumberOfArguments(1);
        Map map = request.getArgument(0, Map.class);
        return map.keySet();
    }
}
