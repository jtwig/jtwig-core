package org.jtwig.resource;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.resource.resolver.CompositeResourceResolver;
import org.jtwig.resource.resolver.ResourceResolver;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeResourceResolverTest {
    private final Environment environment = mock(Environment.class);
    private Collection<ResourceResolver> resolvers = new ArrayList<>();
    private CompositeResourceResolver underTest = new CompositeResourceResolver(resolvers);

    @Before
    public void setUp() throws Exception {
        resolvers.clear();
    }

    @Test
    public void resolveResourceWhenNoneResolves() throws Exception {
        Resource resource = mock(Resource.class);
        String relativePath = "/ba";
        ResourceResolver resourceResolver = mock(ResourceResolver.class);
        resolvers.add(resourceResolver);
        when(resourceResolver.resolve(environment, resource, relativePath)).thenReturn(Optional.<Resource>absent());

        Optional<Resource> result = underTest.resolve(environment, resource, relativePath);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveResourceWhenSomeResolves() throws Exception {
        Resource resource = mock(Resource.class);
        String relativePath = "/ba";
        Resource reference = mock(Resource.class);
        ResourceResolver resourceResolver = mock(ResourceResolver.class);
        resolvers.add(resourceResolver);
        when(resourceResolver.resolve(environment, resource, relativePath)).thenReturn(Optional.of(reference));

        Optional<Resource> result = underTest.resolve(environment, resource, relativePath);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(reference));
    }
}