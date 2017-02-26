package org.jtwig.functions.impl.control;

import org.jtwig.escape.EscapeEngine;
import org.jtwig.escape.NoneEscapeEngine;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.render.context.RenderContext;
import org.jtwig.render.context.RenderContextHolder;

public class RawFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "raw";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.maximumNumberOfArguments(1).minimumNumberOfArguments(1);
        Object value = request.get(0);
        getRenderContext().set(EscapeEngine.class, NoneEscapeEngine.instance());
        return value;
    }

    protected RenderContext getRenderContext() {
        return RenderContextHolder.get();
    }
}
