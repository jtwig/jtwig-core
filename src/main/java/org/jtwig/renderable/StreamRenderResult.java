package org.jtwig.renderable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class StreamRenderResult implements RenderResult {
    private final OutputStream outputStream;
    private final Charset charset;

    public StreamRenderResult(OutputStream outputStream, Charset charset) {
        this.outputStream = outputStream;
        this.charset = charset;
    }

    @Override
    public RenderResult append(String content) {
        try {
            outputStream.write(content.getBytes(charset));
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
