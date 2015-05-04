package org.jtwig.content.json;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.util.ClasspathFinder;

public class JacksonJsonMapperFactory implements JsonMapperFactory {
    public static final String CLASS_NAME = "org.codehaus.jackson.map.ObjectMapper";
    private final ClasspathFinder classpathFinder;

    public JacksonJsonMapperFactory(ClasspathFinder classpathFinder) {
        this.classpathFinder = classpathFinder;
    }

    @Override
    public Optional<Function<Object, String>> create() {
        if (classpathFinder.exists(CLASS_NAME)) {
            return Optional.<Function<Object, String>>of(new JacksonJsonMapper());
        } else {
            return Optional.absent();
        }
    }
    @Override
    public String name() {
        return "Jackson";
    }
}
