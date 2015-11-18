package org.jtwig.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

public class StringResource implements Resource {
    private final String content;
    private final Charset charset;

    public StringResource(String content) {
        this.content = content;
        this.charset = Charset.defaultCharset();
    }

    public StringResource(Charset charset, String content) {
        this.charset = charset;
        this.content = content;
    }

    @Override
    public InputStream getContent() {
        return new ByteArrayInputStream(content.getBytes());
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringResource that = (StringResource) o;

        return content.equals(that.content);

    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
