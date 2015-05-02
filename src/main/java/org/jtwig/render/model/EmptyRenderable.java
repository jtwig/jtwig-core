package org.jtwig.render.model;

import org.jtwig.render.Renderable;

import java.io.OutputStream;

public class EmptyRenderable implements Renderable {
    private static final EmptyRenderable instance = new EmptyRenderable();
    public static EmptyRenderable instance () {
        return instance;
    }

    private EmptyRenderable () {}

    @Override
    public void accept(OutputStream outputStream) {

    }
}
