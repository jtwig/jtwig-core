package org.jtwig.render.model;

import org.jtwig.context.model.EscapeMode;
import org.jtwig.render.RenderException;
import org.jtwig.render.Renderable;

import java.io.IOException;
import java.io.OutputStream;

public class StringRenderable implements Renderable {
    private final String content;
    private final EscapeMode mode;

    public StringRenderable(String content, EscapeMode mode) {
        this.content = content;
        this.mode = mode;
    }

    @Override
    public void accept(OutputStream outputStream) {
        try {
            outputStream.write(mode.escape(content).getBytes());
        } catch (IOException e) {
            throw new RenderException(e);
        }
    }
}
