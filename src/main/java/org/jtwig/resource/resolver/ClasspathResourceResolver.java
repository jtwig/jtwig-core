package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.resource.ClasspathResource;
import org.jtwig.resource.Resource;
import org.jtwig.resource.classpath.ClasspathResourceLoader;
import org.jtwig.resource.util.RelativePathResolver;

import java.io.File;

public class ClasspathResourceResolver implements ResourceResolver {
    public static final String PREFIX = "classpath:";
    private final ClasspathResourceLoader classpathResourceLoader;
    private final RelativePathResolver relativePathResolver;

    public ClasspathResourceResolver(ClasspathResourceLoader classpathResourceLoader, RelativePathResolver relativePathResolver) {
        this.classpathResourceLoader = classpathResourceLoader;
        this.relativePathResolver = relativePathResolver;
    }

    @Override
    public Optional<Resource> resolve(Environment environment, Resource resource, String path) {
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
        if (!classpathResourceLoader.exists(path)) {
            return Optional.absent();
        } else {
            return Optional.<Resource>of(new ClasspathResource(path, classpathResourceLoader));
        }
    }
}
