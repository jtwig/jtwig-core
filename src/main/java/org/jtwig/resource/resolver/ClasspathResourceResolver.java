package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.resource.ClasspathResource;
import org.jtwig.resource.Resource;
import org.jtwig.resource.classpath.ResourceLoader;
import org.jtwig.resource.util.RelativePathResolver;

import java.io.File;

public class ClasspathResourceResolver implements ResourceResolver {
    public static final String PREFIX = "classpath:";
    private final ResourceLoader resourceLoader;
    private final RelativePathResolver relativePathResolver;

    public ClasspathResourceResolver(ResourceLoader resourceLoader, RelativePathResolver relativePathResolver) {
        this.resourceLoader = resourceLoader;
        this.relativePathResolver = relativePathResolver;
    }

    @Override
    public Optional<Resource> resolve(Resource resource, String path) {
        if (path.startsWith(PREFIX)) {
            path = path.substring(PREFIX.length());
        }

        File file = new File(path);
        if (!file.isAbsolute()) {
            if (resource instanceof ClasspathResource) {
                String originalPath = ((ClasspathResource) resource).getPath();
                String absolutePath = relativePathResolver.resolve(originalPath, path);
                return resolve(absolutePath);
            } else {
                return Optional.absent();
            }
        } else {
            return resolve(path);
        }
    }

    private Optional<Resource> resolve(String path) {
        if (!resourceLoader.exists(path)) {
            return Optional.absent();
        } else {
            return Optional.<Resource>of(new ClasspathResource(path, resourceLoader));
        }
    }
}
