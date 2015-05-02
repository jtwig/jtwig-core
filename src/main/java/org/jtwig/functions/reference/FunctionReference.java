package org.jtwig.functions.reference;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionArgument;

import java.util.List;

public interface FunctionReference {
    String name ();
    Optional<Supplier> calculate(List<FunctionArgument> arguments);
}
