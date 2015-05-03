package org.jtwig.render.impl;

import org.jtwig.render.RenderResult;
import org.jtwig.render.Renderable;

public class EmptyRenderable implements Renderable {
    private static final EmptyRenderable instance = new EmptyRenderable();
    public static EmptyRenderable instance () {
        return instance;
    }

    private EmptyRenderable () {}

    @Override
    public void appendTo(RenderResult result) {

    }
}
