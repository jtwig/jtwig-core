package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionArgument;

import java.util.List;

public interface FunctionResolver {
    Optional<Supplier> resolve (String functionName, List<FunctionArgument> arguments);
}
