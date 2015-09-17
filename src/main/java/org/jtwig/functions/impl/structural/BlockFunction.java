package org.jtwig.functions.impl.structural;

import com.google.common.base.Function;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.render.Renderable;
import org.jtwig.render.StringBuilderRenderResult;

public class BlockFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "block";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(1);
        String name = request.getArgument(0, String.class);
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

    protected RenderContext getRenderContext() {
        return RenderContextHolder.get();
    }
}
