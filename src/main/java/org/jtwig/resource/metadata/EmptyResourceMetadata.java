package org.jtwig.resource.metadata;

import com.google.common.base.Optional;
import org.jtwig.resource.exceptions.ResourceException;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public class EmptyResourceMetadata implements ResourceMetadata {
    private static final EmptyResourceMetadata INSTANCE = new EmptyResourceMetadata();

    public static EmptyResourceMetadata instance () {
        return INSTANCE;
    }

    private EmptyResourceMetadata() {}

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public InputStream load() {
        throw new ResourceException("Empty resource");
    }

    @Override
    public Optional<Charset> getCharset() {
        return Optional.absent();
    }

    @Override
    public Optional<URL> toUrl() {
        return Optional.absent();
    }
}
