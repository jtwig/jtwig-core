package org.jtwig.functions.reference;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionArgument;

import java.util.Collection;
import java.util.List;

public class CompositeFunctionReference implements FunctionReference {
    private final String name;
    private final Collection<FunctionReference> functions;

    public CompositeFunctionReference(String name, Collection<FunctionReference> functions) {
        this.name = name;
        this.functions = functions;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Optional<Supplier> calculate(List<FunctionArgument> arguments) {
        for (FunctionReference function : functions) {
            Optional<Supplier> result = function.calculate(arguments);
            if (result.isPresent()) {
                return result;
            }
        }

        return Optional.absent();
    }
}
