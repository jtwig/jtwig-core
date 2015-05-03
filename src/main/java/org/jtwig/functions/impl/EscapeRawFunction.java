package org.jtwig.functions.impl;

import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;

public class EscapeRawFunction {
    @JtwigFunction("raw")
    public Object raw(@Parameter("value") Object value) {
        getRenderContext().currentNode().mode(EscapeMode.NONE);
        return value;
    }

    @JtwigFunction("escape")
    public Object escape(@Parameter("value") Object value) {
        getRenderContext().currentNode().mode(EscapeMode.HTML);
        return value;
    }


    @JtwigFunction("escape")
    public Object escape(@Parameter("value") Object value, @Parameter("mode") String mode) {
        getRenderContext().currentNode().mode(EscapeMode.valueOf(mode.toUpperCase()));
        return value;
    }

    protected RenderContext getRenderContext() {
        return RenderContextHolder.get();
    }
}
