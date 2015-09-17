package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;

import java.util.List;

public interface FunctionResolver {
    Optional<Supplier> resolve (Position position, String functionName, List<JtwigValue> arguments);
}
