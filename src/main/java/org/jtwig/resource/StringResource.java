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
}
