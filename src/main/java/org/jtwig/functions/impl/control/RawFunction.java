package org.jtwig.functions.impl.control;

import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class RawFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "raw";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.maximumNumberOfArguments(1).minimumNumberOfArguments(1);
        getRenderContext().currentNode().mode(EscapeMode.NONE);
        return request.getArgument(0, Object.class);
    }

    protected RenderContext getRenderContext() {
        return RenderContextHolder.get();
    }
}
