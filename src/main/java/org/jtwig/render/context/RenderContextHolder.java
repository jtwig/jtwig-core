package org.jtwig.render.context;

import org.jtwig.render.context.model.RenderContext;

public class RenderContextHolder {
    private static final ThreadLocal<RenderContext> current = new ThreadLocal<>();

    private RenderContextHolder () {}

    public static RenderContext set (RenderContext context) {
        current.set(context);
        return context;
    }

    public static RenderContext get () {
        return current.get();
    }
}
