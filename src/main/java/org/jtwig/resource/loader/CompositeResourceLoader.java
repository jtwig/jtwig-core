package org.jtwig.resource.loader;

import com.google.common.base.Optional;
import org.jtwig.resource.exceptions.ResourceNotFoundException;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;

public class CompositeResourceLoader implements ResourceLoader {
    private final Collection<ResourceLoader> resourceLoaders;

    public CompositeResourceLoader(Collection<ResourceLoader> resourceLoaders) {
        this.resourceLoaders = resourceLoaders;
    }

    @Override
    public Optional<Charset> getCharset(String path) {
        for (ResourceLoader resourceLoader : resourceLoaders) {
            if (resourceLoader.exists(path))
                return resourceLoader.getCharset(path);
        }
        return Optional.absent();
    }

    @Override
    public InputStream load(String path) {
        for (ResourceLoader resourceLoader : resourceLoaders) {
            if (resourceLoader.exists(path)) {
                return resourceLoader.load(path);
            }
        }
        throw new ResourceNotFoundException(String.format("Resource '%s' not found", path));
    }

    @Override
    public boolean exists(String path) {
        for (ResourceLoader resourceLoader : resourceLoaders) {
            if (resourceLoader.exists(path))
                return true;
        }
        return false;
    }

    @Override
    public Optional<URL> toUrl(String path) {
        for (ResourceLoader resourceLoader : resourceLoaders) {
            if (resourceLoader.exists(path)) {
                return resourceLoader.toUrl(path);
            }
        }
        return Optional.absent();
    }
}
