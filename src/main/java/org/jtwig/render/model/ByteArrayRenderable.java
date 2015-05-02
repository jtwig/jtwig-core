package org.jtwig.render.model;

import org.jtwig.render.RenderException;
import org.jtwig.render.Renderable;

import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayRenderable implements Renderable {
    private final byte[] content;

    public ByteArrayRenderable(byte[] content) {
        this.content = content;
    }

    @Override
    public void accept(OutputStream outputStream) {
        try {
            outputStream.write(content);
        } catch (IOException e) {
            throw new RenderException(e);
        }
    }
}
