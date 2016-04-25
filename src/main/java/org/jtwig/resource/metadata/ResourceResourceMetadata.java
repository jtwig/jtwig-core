package org.jtwig.resource.metadata;

import com.google.common.base.Optional;
import org.jtwig.resource.loader.ResourceLoader;
import org.jtwig.resource.reference.ResourceReference;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public class ResourceResourceMetadata implements ResourceMetadata {
    private final ResourceLoader resourceLoader;
    private final ResourceReference resourceReference;

    public ResourceResourceMetadata(ResourceLoader resourceLoader, ResourceReference resourceReference) {
        this.resourceLoader = resourceLoader;
        this.resourceReference = resourceReference;
    }

    @Override
    public boolean exists() {
        return resourceLoader.exists(resourceReference.getPath());
    }

    @Override
    public InputStream load() {
        return resourceLoader.load(resourceReference.getPath());
    }

    @Override
    public Optional<Charset> getCharset() {
        return resourceLoader.getCharset(resourceReference.getPath());
    }

    @Override
    public Optional<URL> toUrl () {
        return resourceLoader.toUrl(resourceReference.getPath());
    }

    public ResourceReference getResourceReference() {
        return resourceReference;
    }
}
