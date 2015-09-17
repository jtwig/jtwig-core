package org.jtwig.functions.impl.control;

import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class EscapeFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "escape";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(2);

        EscapeMode escapeMode = EscapeMode.HTML;
        if (request.getNumberOfArguments() == 2) {
            escapeMode = EscapeMode.valueOf(request.getArgument(1, String.class).toUpperCase());
        }

        getRenderContext().currentNode().mode(escapeMode);
        return request.getArgument(0, Object.class);
    }

    protected RenderContext getRenderContext() {
        return RenderContextHolder.get();
    }
}
