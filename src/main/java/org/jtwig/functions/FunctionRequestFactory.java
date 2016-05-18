package org.jtwig.functions;

import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

public class FunctionRequestFactory {
    public FunctionRequest create (RenderRequest request, Position position, String functionName, FunctionArguments arguments) {
        return new FunctionRequest(
                request, position, functionName,
                arguments
        );
    }
}
