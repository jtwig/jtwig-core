package org.jtwig.functions.impl.logical;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.convert.Converter;

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
    public Object execute(FunctionRequest request) {
        return nullFunction.execute(request) ||
                !definedFunction.execute(request) ||
                isEmptyIterable(request) ||
                isZero(request)
                ;
    }

    private boolean isZero(FunctionRequest request) {
        Converter<BigDecimal> numberConverter = request.getEnvironment().getValueEnvironment().getNumberConverter();

        Converter.Result<BigDecimal> decimalResult = numberConverter.convert(request.get(0));

        if (decimalResult.isDefined()) {
            return BigDecimal.ZERO.equals(decimalResult.get());
        } else {
            return false;
        }
    }

    private boolean isEmptyIterable(FunctionRequest request) {
        if (iterableFunction.execute(request)) {
            Object input = request.get(0);
            if (input instanceof Map) {
                return ((Map) input).isEmpty();
            }

            if (input instanceof Iterable) {
                return !((Iterable) input).iterator().hasNext();
            }

            return ((Object[]) input).length == 0;
        }
        return false;
    }
}
