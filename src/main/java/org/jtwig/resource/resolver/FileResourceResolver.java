package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.resource.FileResource;
import org.jtwig.resource.Resource;

import java.io.File;

public class FileResourceResolver implements ResourceResolver {
    public static final String PREFIX = "file:";

    @Override
    public Optional<Resource> resolve(Environment environment, Resource resource, String relativePath) {
        if (relativePath.startsWith(PREFIX)) {
            relativePath = relativePath.substring(PREFIX.length());
        }

        File relativeFile = new File(relativePath);
        if (!relativeFile.isAbsolute()) {
            if (resource instanceof FileResource) {
                File parentFile = ((FileResource) resource).getFile().getParentFile();
                File file = new File(parentFile, relativePath);
                return resolve(environment, file);
            } else {
                return resolve(environment, relativeFile);
            }
        } else {
            return resolve(environment, relativeFile);
        }
    }

    private Optional<Resource> resolve(Environment environment, File relativeFile) {
        if (relativeFile.exists()) {
            return Optional.<Resource>of(new FileResource(environment.resources().getDefaultInputCharset(), relativeFile));
        } else {
            return Optional.absent();
        }
    }
}
