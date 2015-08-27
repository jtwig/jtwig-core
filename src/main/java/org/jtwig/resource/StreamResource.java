package org.jtwig.resource;

import java.io.InputStream;

public class StreamResource implements Resource {
    private final InputStream inputStream;

    public StreamResource(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public InputStream content() {
        return inputStream;
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
