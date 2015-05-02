package org.jtwig.resource.util;

import org.jtwig.resource.exceptions.ResourceException;

import java.io.File;
import java.io.IOException;

public class RelativePathResolver {
    public String resolve(String origin, String relativePath) {
        try {
            return newFile(newFile(origin).getParentFile(), relativePath).getCanonicalPath();
        } catch (IOException e) {
            throw new ResourceException(String.format("Unable to get canonical path for resource '%s' relative to '%s'", relativePath, origin), e);
        }
    }

    protected File newFile(File parentFile, String relativePath) {
        return new File(parentFile, relativePath);
    }

    protected File newFile(String origin) {
        return new File(origin);
    }
}
