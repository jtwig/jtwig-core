package org.jtwig.functions.resolver;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.reference.FunctionReference;

import java.util.List;
import java.util.Map;

public class CoreFunctionResolver implements FunctionResolver {
    private final Map<String, FunctionReference> functions;

    public CoreFunctionResolver(Map<String, FunctionReference> functions) {
        this.functions = functions;
    }

    @Override
    public Optional<Supplier> resolve(String functionName, final List<FunctionArgument> arguments) {
        return Optional
                .fromNullable(functions.get(functionName))
                .transform(new Function<FunctionReference, Optional<Supplier>>() {
                    @Override
                    public Optional<Supplier> apply(FunctionReference input) {
                        return input.calculate(arguments);
                    }
                })
                .or(Optional.<Supplier>absent());
    }
}
