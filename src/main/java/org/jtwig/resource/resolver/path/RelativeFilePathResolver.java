package org.jtwig.resource.resolver.path;

import java.io.File;

public class RelativeFilePathResolver implements RelativeReferenceResolver {
    private static final RelativeFilePathResolver INSTANCE = new RelativeFilePathResolver();

    public static RelativeFilePathResolver instance () {
        return INSTANCE;
    }

    private RelativeFilePathResolver() {}

    @Override
    public boolean isRelative(String path) {
        return !new File(path).isAbsolute();
    }

    @Override
    public String resolve(String parent, String child) {
        return new File(new File(parent).getParentFile(), child).getPath();
    }
}
