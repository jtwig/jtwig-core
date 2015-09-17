package org.jtwig.functions.resolver;

import org.jtwig.exceptions.InvalidFunctionNameException;
import org.jtwig.functions.JtwigFunction;

import java.util.HashMap;

public class FunctionResolverFactory {

    public static final String IDENTIFIER_PATTERN = "[A-Za-z_$][A-Za-z0-9_$]*";

    public FunctionResolver create(FunctionResolverConfiguration configuration) {
        HashMap<String, JtwigFunction> map = new HashMap<>();
        for (JtwigFunction jtwigFunction : configuration.getFunctions()) {
            validate(jtwigFunction.name());
            map.put(jtwigFunction.name(), jtwigFunction);
            for (String alias : jtwigFunction.aliases()) {
                validate(alias);
                map.put(alias, jtwigFunction);
            }
        }
        return new CoreFunctionResolver(map);
    }

    private void validate(String name) {
        if (!name.matches(IDENTIFIER_PATTERN)) {
            throw new InvalidFunctionNameException(String.format("Function name %s is invalid, it should be an identifier (regular expression: %s)", name, IDENTIFIER_PATTERN));
        }
    }
}
