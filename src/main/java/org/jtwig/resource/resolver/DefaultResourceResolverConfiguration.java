package org.jtwig.resource.resolver;

import org.jtwig.resource.classpath.DefaultClasspathResourceLoader;
import org.jtwig.resource.util.RelativePathResolver;

import static java.util.Arrays.asList;

public class DefaultResourceResolverConfiguration extends ResourceResolverConfiguration {
    public DefaultResourceResolverConfiguration() {
        super(asList(
                new FileResourceResolver(),
                new ClasspathResourceResolver(new DefaultClasspathResourceLoader(DefaultResourceResolverConfiguration.class.getClassLoader()), new RelativePathResolver())
        ));
    }
}
