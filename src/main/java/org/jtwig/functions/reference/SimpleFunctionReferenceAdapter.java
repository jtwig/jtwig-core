package org.jtwig.functions.reference;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.Collections2;
import org.jtwig.reflection.Executable;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.reference.FunctionReference;
import org.jtwig.functions.SimpleFunction;

import java.util.List;

public class SimpleFunctionReferenceAdapter implements FunctionReference {
    private final SimpleFunction function;

    public SimpleFunctionReferenceAdapter(SimpleFunction function) {
        this.function = function;
    }

    @Override
    public String name() {
        return function.name();
    }

    @Override
    public Optional<Supplier> calculate(List<FunctionArgument> arguments) {
        Object[] argumentsArray = Collections2.transform(arguments, new Function<FunctionArgument, Object>() {
            @Override
            public Object apply(FunctionArgument input) {
                return input.getValue();
            }
        }).toArray();

        Supplier supplier = new FunctionExecution(function.execute(argumentsArray));
        return Optional.of(supplier);
    }

    private static class FunctionExecution implements Supplier {
        private final Object result;

        public FunctionExecution(Object result) {
            this.result = result;
        }

        @Override
        public Object get() {
            return result;
        }
    }
}
