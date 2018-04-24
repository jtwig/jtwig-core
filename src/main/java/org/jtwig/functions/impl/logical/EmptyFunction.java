package org.jtwig.functions.impl.logical;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

import java.math.BigDecimal;
import org.jtwig.value.convert.string.StringConverter;

public class EmptyFunction extends SimpleJtwigFunction {
    private final DefinedFunction definedFunction = new DefinedFunction();

    @Override
    public String name() {
        return "empty";
    }

    @Override
    public Object execute(FunctionRequest request) {
        return request.get(0) == null ||
                !definedFunction.execute(request) ||
                isEmptyIterable(request) ||
                isZero(request) ||
                isEmptyString(request)
                ;
    }

    private boolean isZero(FunctionRequest request) {
        Converter<BigDecimal> numberConverter = request.getEnvironment().getValueEnvironment().getNumberConverter();
        Converter.Result<BigDecimal> decimalResult = numberConverter.convert(request.get(0));
        return decimalResult.isDefined() && BigDecimal.ZERO.equals(decimalResult.get());
    }

    private boolean isEmptyIterable(FunctionRequest request) {
        Converter.Result<WrappedCollection> result = request.getEnvironment().getValueEnvironment().getCollectionConverter().convert(request.get(0));
        return result.isDefined() && result.get().size() == 0;
    }

    private boolean isEmptyString(FunctionRequest request) {
        StringConverter converter = request.getEnvironment().getValueEnvironment().getStringConverter();
        String param = converter.convert(request.get(0));
        return param.isEmpty();
    }
}
