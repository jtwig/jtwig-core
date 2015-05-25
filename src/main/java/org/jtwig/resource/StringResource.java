package org.jtwig.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StringResource implements Resource {
    private final String content;

    public StringResource(String content) {
        this.content = content;
    }

    @Override
    public InputStream content() {
        return new ByteArrayInputStream(content.getBytes());
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
