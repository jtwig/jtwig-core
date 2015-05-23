package org.jtwig.content.json;

import com.google.common.base.Function;
import org.jtwig.util.ClasspathFinder;

public class Jackson2JsonMapperProvider implements JsonMapperProvider {
    public static final String CLASS_NAME = "com.fasterxml.jackson.databind.ObjectMapper";
    private final ClasspathFinder classpathFinder;

    public Jackson2JsonMapperProvider(ClasspathFinder classpathFinder) {
        this.classpathFinder = classpathFinder;
    }


    @Override
    public boolean isFound() {
        return classpathFinder.exists(CLASS_NAME);
    }

    @Override
    public Function<Object, String> jsonMapper() {
        return new Jackson2JsonMapper();
    }
}
