package org.jtwig.resource.resolver;

import org.jtwig.resource.classpath.ResourceLoader;
import org.jtwig.resource.util.RelativePathResolver;

import java.util.Collection;

import static java.util.Arrays.asList;

public class DefaultResourceResolverConfiguration extends ResourceResolverConfiguration {
    public DefaultResourceResolverConfiguration() {
        super(asList(
                new FileResourceResolver(),
                new ClasspathResourceResolver(new ResourceLoader(DefaultResourceResolverConfiguration.class.getClassLoader()), new RelativePathResolver())
        ));
    }
}
