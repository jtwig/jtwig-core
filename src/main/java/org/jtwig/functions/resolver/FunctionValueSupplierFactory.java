package org.jtwig.functions.resolver;

import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.JtwigFunction;

public class FunctionValueSupplierFactory {
    public Supplier<Object> create (JtwigFunction function, FunctionRequest request) {
        return new FunctionValueSupplier(function, request);
    }
}
