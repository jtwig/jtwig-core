package org.jtwig.content.json;

import com.google.common.base.Function;
import org.jtwig.util.ClasspathFinder;

public class JacksonJsonMapperProvider implements JsonMapperProvider {
    public static final String CLASS_NAME = "org.codehaus.jackson.map.ObjectMapper";
    private final ClasspathFinder classpathFinder;

    public JacksonJsonMapperProvider(ClasspathFinder classpathFinder) {
        this.classpathFinder = classpathFinder;
    }

    @Override
    public boolean isFound() {
        return classpathFinder.exists(CLASS_NAME);
    }

    @Override
    public Function<Object, String> jsonMapper() {
        return new JacksonJsonMapper();
    }
}
