package org.jtwig.resource.config;

import org.jtwig.resource.classpath.DefaultClasspathResourceLoader;
import org.jtwig.resource.resolver.ClasspathResourceResolver;
import org.jtwig.resource.resolver.FileResourceResolver;
import org.jtwig.resource.util.RelativePathResolver;

import java.nio.charset.Charset;

import static java.util.Arrays.asList;

public class DefaultResourceConfiguration extends ResourceConfiguration {
    public DefaultResourceConfiguration() {
        super(asList(
                new FileResourceResolver(),
                new ClasspathResourceResolver(new DefaultClasspathResourceLoader(DefaultResourceConfiguration.class.getClassLoader()), new RelativePathResolver())
        ), Charset.defaultCharset());
    }
}
