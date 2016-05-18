package org.jtwig.functions.resolver;

import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.JtwigFunction;

public class FunctionValueSupplier implements Supplier<Object> {
    private final JtwigFunction jtwigFunction;
    private final FunctionRequest request;

    public FunctionValueSupplier(JtwigFunction jtwigFunction, FunctionRequest request) {
        this.jtwigFunction = jtwigFunction;
        this.request = request;
    }

    @Override
    public Object get() {
        return jtwigFunction.execute(request);
    }
}
