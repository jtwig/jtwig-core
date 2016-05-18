package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionArguments;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

public interface FunctionResolver {
    Optional<Supplier<Object>> resolve (RenderRequest request, Position position, String functionName, FunctionArguments arguments);
}
