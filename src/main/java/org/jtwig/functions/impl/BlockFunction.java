package org.jtwig.functions.impl;

import com.google.common.base.Function;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.render.Renderable;
import org.jtwig.render.StringBuilderRenderResult;

public class BlockFunction {
    @JtwigFunction("block")
    public String block (@Parameter("name") String name) {
        return getRenderContext().currentResource()
                .block(name)
                .transform(new Function<Renderable, String>() {
                    @Override
                    public String apply(Renderable input) {
                        return input.appendTo(new StringBuilderRenderResult()).content();
                    }
                })
                .or("");
    }

    private RenderContext getRenderContext() {
        return RenderContextHolder.get();
    }
}
