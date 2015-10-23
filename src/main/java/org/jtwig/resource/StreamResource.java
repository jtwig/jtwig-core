package org.jtwig.resource;

import java.io.InputStream;
import java.nio.charset.Charset;

public class StreamResource implements Resource {
    private final Charset charset;
    private final InputStream inputStream;

    public StreamResource(InputStream inputStream) {
        this.charset = Charset.defaultCharset();
        this.inputStream = inputStream;
    }

    public StreamResource(Charset charset, InputStream inputStream) {
        this.charset = charset;
        this.inputStream = inputStream;
    }

    @Override
    public InputStream getContent() {
        return inputStream;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StreamResource that = (StreamResource) o;

        return inputStream.equals(that.inputStream);
    }

    @Override
    public int hashCode() {
        return inputStream.hashCode();
    }
}
