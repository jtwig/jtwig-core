package org.jtwig.functions.impl;

import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.render.StringBuilderRenderResult;
import org.jtwig.render.impl.OverrideRenderable;
import org.jtwig.util.OptionalUtils;

public class ParentFunction {
    @JtwigFunction("parent")
    public String parent () {
        RenderContext renderContext = getRenderContext();

        OverrideRenderable renderable = renderContext.currentResource().currentBlock()
                .or(OptionalUtils.<OverrideRenderable, CalculationException>throwException(new CalculationException("Parent function can only be called inside a block element")));

        return renderable.getDefault()
                .appendTo(new StringBuilderRenderResult())
                .content();
    }

    protected RenderContext getRenderContext() {
        return RenderContextHolder.get();
    }
}
