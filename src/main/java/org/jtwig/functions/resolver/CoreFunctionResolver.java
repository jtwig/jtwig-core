package org.jtwig.functions.resolver;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.JtwigFunction;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

import java.util.List;
import java.util.Map;

public class CoreFunctionResolver implements FunctionResolver {
    private final Map<String, JtwigFunction> functions;

    public CoreFunctionResolver(Map<String, JtwigFunction> functions) {
        this.functions = functions;
    }

    @Override
    public Optional<Supplier> resolve(final RenderRequest request, final Position position, final String functionName, final List<Object> arguments) {
        return Optional
                .fromNullable(functions.get(functionName))
                .transform(new Function<JtwigFunction, Supplier>() {
                    @Override
                    public Supplier apply(final JtwigFunction input) {
                        return new Supplier() {
                            @Override
                            public Object get() {
                                return input.execute(new FunctionRequest(request, position, functionName, arguments));
                            }
                        };
                    }
                })
                .or(Optional.<Supplier>absent());
    }
}
