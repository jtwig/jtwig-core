package org.jtwig.functions.resolver.position.vararg;

import org.jtwig.functions.FunctionArgument;

import java.util.List;

public class ArrayComponentExtractor {
    public Class extract (List<FunctionArgument> input) {
        for (FunctionArgument functionArgument : input) {
            if (functionArgument.getValue() != null) {
                return functionArgument.getValue().getClass();
            }
        }

        return Object.class;
    }
}
