package org.jtwig.content.json;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.util.ClasspathFinder;

public class Jackson2JsonMapperFactory implements JsonMapperFactory {
    public static final String CLASS_NAME = "com.fasterxml.jackson.databind.ObjectMapper";
    private final ClasspathFinder classpathFinder;

    public Jackson2JsonMapperFactory(ClasspathFinder classpathFinder) {
        this.classpathFinder = classpathFinder;
    }


    @Override
    public Optional<Function<Object, String>> create() {
        if (classpathFinder.exists(CLASS_NAME)) {
            return Optional.<Function<Object, String>>of(new Jackson2JsonMapper());
        } else {
            return Optional.absent();
        }
    }

    @Override
    public String name() {
        return "Jackson 2";
    }
}
