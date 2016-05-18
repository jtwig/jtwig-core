package org.jtwig.functions.environment;

import org.jtwig.exceptions.InvalidFunctionNameException;
import org.jtwig.functions.FunctionRequestFactory;
import org.jtwig.functions.JtwigFunction;
import org.jtwig.functions.resolver.CoreFunctionResolver;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.functions.resolver.FunctionValueSupplierFactory;

import java.util.Collection;
import java.util.HashMap;

public class FunctionResolverFactory {
    public static final String IDENTIFIER_PATTERN = "[A-Za-z_$][A-Za-z0-9_$]*";

    public FunctionResolver create(Collection<JtwigFunction> functions) {
        HashMap<String, JtwigFunction> map = new HashMap<>();
        for (JtwigFunction jtwigFunction : functions) {
            validate(jtwigFunction.name());
            map.put(jtwigFunction.name(), jtwigFunction);
            for (String alias : jtwigFunction.aliases()) {
                validate(alias);
                map.put(alias, jtwigFunction);
            }
        }
        return new CoreFunctionResolver(map, new FunctionRequestFactory(), new FunctionValueSupplierFactory());
    }

    private void validate(String name) {
        if (!name.matches(IDENTIFIER_PATTERN)) {
            throw new InvalidFunctionNameException(String.format("Function name %s is invalid, it should be an identifier (regular expression: %s)", name, IDENTIFIER_PATTERN));
        }
    }
}
