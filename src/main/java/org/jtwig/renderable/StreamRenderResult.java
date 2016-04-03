package org.jtwig.renderable;

import java.io.IOException;
import java.io.OutputStream;

public class StreamRenderResult implements RenderResult {
    private final OutputStream outputStream;

    public StreamRenderResult(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public RenderResult append(String content) {
        try {
            outputStream.write(content.getBytes());
        } catch (IOException e) {
            throw new RenderException(e);
        }
        return this;
    }

    @Override
    public RenderResult flush() {
        try {
            outputStream.flush();
        } catch (IOException e) {
            throw new RenderException(e);
        }
        return this;
    }

    @Override
    public String content() {
        throw new UnsupportedOperationException("Cannot gather content from output stream");
    }
}
