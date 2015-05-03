package org.jtwig.render.impl;

import com.google.common.base.Function;
import org.jtwig.render.RenderResult;
import org.jtwig.render.Renderable;

public class TransformRenderable implements Renderable {
    private final Renderable renderable;
    private final Function<String, String> transformer;

    public TransformRenderable(Renderable renderable, Function<String, String> transformer) {
        this.renderable = renderable;
        this.transformer = transformer;
    }

    @Override
    public void appendTo(RenderResult result) {
        RenderResult renderResult = new RenderResult();
        renderable.appendTo(renderResult);
        String transformedResult = transformer.apply(renderResult.toString());
        result.append(transformedResult);
    }
}
