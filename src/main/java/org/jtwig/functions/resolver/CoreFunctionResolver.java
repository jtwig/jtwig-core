package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionArguments;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.FunctionRequestFactory;
import org.jtwig.functions.JtwigFunction;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

import java.util.Map;

public class CoreFunctionResolver implements FunctionResolver {
    private final Map<String, JtwigFunction> functions;
    private final FunctionRequestFactory functionRequestFactory;
    private final FunctionValueSupplierFactory functionValueSupplierFactory;

    public CoreFunctionResolver(Map<String, JtwigFunction> functions, FunctionRequestFactory functionRequestFactory, FunctionValueSupplierFactory functionValueSupplierFactory) {
        this.functions = functions;
        this.functionRequestFactory = functionRequestFactory;
        this.functionValueSupplierFactory = functionValueSupplierFactory;
    }

    @Override
    public Optional<Supplier<Object>> resolve(RenderRequest request, Position position, String functionName, FunctionArguments arguments) {
        Optional<JtwigFunction> functionOptional = Optional.fromNullable(functions.get(functionName));

        if (functionOptional.isPresent()) {
            FunctionRequest functionRequest = functionRequestFactory.create(request, position, functionName, arguments);
            return Optional.of(functionValueSupplierFactory.create(functionOptional.get(), functionRequest));
        } else {
            return Optional.absent();
        }
    }
}
