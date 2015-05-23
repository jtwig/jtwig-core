package org.jtwig.content.json;

import org.jtwig.util.ClasspathFinder;

import static java.util.Arrays.asList;

public class DefaultJsonMapperProviderConfiguration extends JsonMapperProviderConfiguration {
    private static final ClasspathFinder CLASSPATH_FINDER = new ClasspathFinder(DefaultJsonMapperProviderConfiguration.class.getClassLoader());

    public DefaultJsonMapperProviderConfiguration() {
        super(asList(
                new Jackson2JsonMapperProvider(CLASSPATH_FINDER),
                new JacksonJsonMapperProvider(CLASSPATH_FINDER)
        ));
    }
}
