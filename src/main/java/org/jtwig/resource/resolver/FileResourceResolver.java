package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.resource.FileResource;
import org.jtwig.resource.Resource;

import java.io.File;

public class FileResourceResolver implements ResourceResolver {
    public static final String PREFIX = "file:";

    @Override
    public Optional<Resource> resolve(Resource resource, String relativePath) {
        if (relativePath.startsWith(PREFIX)) {
            relativePath = relativePath.substring(PREFIX.length());
        }

        File relativeFile = new File(relativePath);
        if (!relativeFile.isAbsolute()) {
            if (resource instanceof FileResource) {
                File parentFile = ((FileResource) resource).getFile().getParentFile();
                File file = new File(parentFile, relativePath);
                return resolve(file);
            } else {
                return resolve(relativeFile);
            }
        } else {
            return resolve(relativeFile);
        }
    }

    private Optional<Resource> resolve(File relativeFile) {
        if (relativeFile.exists()) {
            return Optional.<Resource>of(new FileResource(relativeFile));
        } else {
            return Optional.absent();
        }
    }
}
