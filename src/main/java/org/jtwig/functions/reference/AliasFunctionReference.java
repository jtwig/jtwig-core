package org.jtwig.functions.reference;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionArgument;

import java.util.List;

public class AliasFunctionReference implements FunctionReference {
    private final String alias;
    private final FunctionReference functionReference;

    public AliasFunctionReference(String alias, FunctionReference functionReference) {
        this.alias = alias;
        this.functionReference = functionReference;
    }

    @Override
    public String name() {
        return alias;
    }

    @Override
    public Optional<Supplier> calculate(List<FunctionArgument> arguments) {
        return functionReference.calculate(arguments);
    }
}
