package org.jtwig.functions.impl.mixed;

import org.jtwig.exceptions.ResolveValueException;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultFunction extends SimpleJtwigFunction {
    private final Logger log = LoggerFactory.getLogger(DefaultFunction.class);

    @Override
    public String name() {
        return "default";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(2).maximumNumberOfArguments(2);
        Object defaultValue = request.get(1);
        try {
            Object input = request.get(0);
            if (input == null || input == Undefined.UNDEFINED) {
                return defaultValue;
            } else {
                return input;
            }
        } catch (ResolveValueException e) {
            log.debug("Unable to evaluate expression", e);
        }

        return defaultValue;
    }
}
