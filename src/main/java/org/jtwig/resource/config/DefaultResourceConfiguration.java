package org.jtwig.resource.config;

import com.google.common.collect.ImmutableList;
import org.jtwig.resource.loader.ClasspathResourceLoader;
import org.jtwig.resource.loader.FileResourceLoader;
import org.jtwig.resource.loader.StringResourceLoader;
import org.jtwig.resource.loader.TypedResourceLoader;
import org.jtwig.resource.reference.DefaultResourceReferenceExtractor;
import org.jtwig.resource.reference.PosixResourceReferenceExtractor;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.resource.reference.UncResourceReferenceExtractor;
import org.jtwig.resource.reference.path.PathTypeSupplier;
import org.jtwig.resource.resolver.ReferenceRelativeResourceResolver;
import org.jtwig.resource.resolver.RelativeResourceResolver;
import org.jtwig.resource.resolver.path.RelativeFilePathResolver;
import org.jtwig.resource.resolver.path.RelativePathResolver;

import java.nio.charset.Charset;

import static java.util.Collections.singleton;

public class DefaultResourceConfiguration extends ResourceConfiguration {
    public DefaultResourceConfiguration() {
        super(ImmutableList.<RelativeResourceResolver>builder()
                .add(new ReferenceRelativeResourceResolver(singleton(ResourceReference.CLASSPATH), RelativePathResolver.instance()))
                .add(new ReferenceRelativeResourceResolver(singleton(ResourceReference.FILE), RelativeFilePathResolver.instance()))
                .build(),
                ImmutableList.of(ResourceReference.STRING, ResourceReference.MEMORY),
                ImmutableList.of(
                        new TypedResourceLoader(ResourceReference.FILE, FileResourceLoader.instance()),
                        new TypedResourceLoader(ResourceReference.CLASSPATH, new ClasspathResourceLoader(DefaultResourceConfiguration.class.getClassLoader())),
                        new TypedResourceLoader(ResourceReference.STRING, StringResourceLoader.instance())
                ),
                new DefaultResourceReferenceExtractor(PathTypeSupplier.pathTypeSupplier(), new PosixResourceReferenceExtractor(), new UncResourceReferenceExtractor()),
                Charset.defaultCharset());
    }
}
