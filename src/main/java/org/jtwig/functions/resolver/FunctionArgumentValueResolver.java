package org.jtwig.functions.resolver;

import org.jtwig.functions.FunctionArgument;
import org.jtwig.reflection.input.InputParameterValueResolver;

public class FunctionArgumentValueResolver implements InputParameterValueResolver<FunctionArgument> {
    @Override
    public Object resolve(FunctionArgument functionArgument) {
        return functionArgument.getValue();
    }
}
