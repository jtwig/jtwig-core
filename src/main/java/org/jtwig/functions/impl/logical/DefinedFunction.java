package org.jtwig.functions.impl.logical;

import org.jtwig.exceptions.ResolveValueException;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.Undefined;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefinedFunction extends SimpleJtwigFunction {
    private final Logger log = LoggerFactory.getLogger(DefinedFunction.class);

    @Override
    public String name() {
        return "defined";
    }

    @Override
    public Boolean execute(FunctionRequest request) {
        request.maximumNumberOfArguments(1);
        request.minimumNumberOfArguments(1);

        try {
            return request.get(0) != Undefined.UNDEFINED;
        } catch (ResolveValueException e){
            log.debug("Unable to evaluate expression", e);
            return false;
        }
    }
}
