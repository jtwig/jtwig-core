package org.jtwig.resource.reference.path;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

public class PathTypeSupplier implements Supplier<PathType> {
    public static final String OS_NAME = "os.name";
    public static final String WIN = "win";
    private static final PathTypeSupplier INSTANCE = new PathTypeSupplier(new Function<String, String>() {
        @Override
        public String apply(String input) {
            return System.getProperty(input);
        }
    });

    public static PathTypeSupplier pathTypeSupplier () {
        return INSTANCE;
    }

    private final PathType type;

    PathTypeSupplier(Function<String, String> systemProperties) {
        String operatingSystem = systemProperties.apply(OS_NAME).toLowerCase();
        if (operatingSystem.contains(WIN)) {
            type = PathType.UNC;
        } else {
            type = PathType.POSIX;
        }
    }

    @Override
    public PathType get() {
        return type;
    }
}
