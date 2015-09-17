package org.jtwig.functions.impl.logical;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.math.BigDecimal;
import java.util.Map;

public class EmptyFunction extends SimpleJtwigFunction {
    private final NullFunction nullFunction = new NullFunction();
    private final DefinedFunction definedFunction = new DefinedFunction();
    private final IterableFunction iterableFunction = new IterableFunction();

    @Override
    public String name() {
        return "empty";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        return nullFunction.execute(request) ||
                !definedFunction.execute(request) ||
                isEmptyIterable(request) ||
                isZero(request)
                ;
    }

    private boolean isZero(JtwigFunctionRequest request) {
        Optional<BigDecimal> number = request.get(0).asNumber();
        return number.transform(new Function<BigDecimal, Boolean>() {
            @Override
            public Boolean apply(BigDecimal input) {
                return BigDecimal.ZERO.equals(input);
            }
        }).or(false);
    }

    private boolean isEmptyIterable(JtwigFunctionRequest request) {
        if (iterableFunction.execute(request)) {
            Object input = request.getArgument(0, Object.class);
            if (input instanceof Map) {
                return ((Map) input).isEmpty();
            }

            if (input instanceof Iterable) {
                return !((Iterable) input).iterator().hasNext();
            }

            if (input.getClass().isArray()) {
                return ((Object[]) input).length == 0;
            }
        }
        return false;
    }
}
