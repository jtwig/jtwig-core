package org.jtwig.resource;

import com.google.common.base.Optional;
import org.jtwig.resource.classpath.ResourceLoader;
import org.jtwig.resource.resolver.ClasspathResourceResolver;
import org.jtwig.resource.util.RelativePathResolver;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClasspathResourceResolverTest {
    private final ResourceLoader resourceLoader = mock(ResourceLoader.class);
    private ClasspathResourceResolver underTest = new ClasspathResourceResolver(resourceLoader, new RelativePathResolver());

    @Test
    public void resolveAbsoluteExisting() throws Exception {
        String path = "/test";
        when(resourceLoader.exists(path)).thenReturn(true);

        Optional<Resource> result = underTest.resolve(new ClasspathResource("/one.twig", resourceLoader), path);

        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void resolveAbsoluteMissing() throws Exception {
        String path = "/test";
        when(resourceLoader.exists(path)).thenReturn(false);

        Optional<Resource> result = underTest.resolve(new ClasspathResource("/one.twig", resourceLoader), path);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveRelativeExisting() throws Exception {
        String path = "test";
        when(resourceLoader.exists("/test")).thenReturn(true);

        Optional<Resource> result = underTest.resolve(new ClasspathResource("/one.twig", resourceLoader), path);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), instanceOf(ClasspathResource.class));
    }

    @Test
    public void resolveRelativeMissing() throws Exception {
        String path = "test";
        when(resourceLoader.exists("/test")).thenReturn(false);

        Optional<Resource> result = underTest.resolve(new ClasspathResource("/one.twig", resourceLoader), path);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveRelativeNonClasspath() throws Exception {
        String path = "test";

        Optional<Resource> result = underTest.resolve(mock(Resource.class), path);

        assertThat(result.isPresent(), is(false));
    }
}