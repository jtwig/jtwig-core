package org.jtwig.resource.resolver.path;

import org.jtwig.resource.exceptions.ResourceException;

import java.io.File;
import java.io.IOException;

public class RelativePathResolver {
    public static final String ROOT_PATH = "/";
    public static final String WINDOWS_ROOT = "\\";
    public static final String WINDOWS_DRIVE_PATTERN = "^\\w:\\\\.*";
    private static final RelativePathResolver INSTANCE = new RelativePathResolver();

    public static RelativePathResolver instance () {
        return INSTANCE;
    }

    private RelativePathResolver () {}

    public boolean isRelative(String path) {
        return !path.startsWith(ROOT_PATH)
                && !path.startsWith(WINDOWS_ROOT)
                && !path.matches(WINDOWS_DRIVE_PATTERN);
    }

    public String resolve(String parent, String child) {
        File newFile = new File(new File(parent).getParentFile(), child);
        try {
            return newFile.getCanonicalPath();
        } catch (IOException e) {
            throw new ResourceException(String.format("Canonical path ('%s', '%s') = '%s' is invalid", parent, child, newFile.getPath()));
        }
    }
}
