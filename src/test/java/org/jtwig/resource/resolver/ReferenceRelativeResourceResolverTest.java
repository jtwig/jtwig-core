package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.resource.resolver.path.RelativePathResolver;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReferenceRelativeResourceResolverTest {
    private final ArrayList<String> types = new ArrayList<>();
    private final RelativePathResolver pathResolver = mock(RelativePathResolver.class);
    private ReferenceRelativeResourceResolver underTest = new ReferenceRelativeResourceResolver(types, pathResolver);

    @Before
    public void setUp() throws Exception {
        types.clear();
    }

    @Test
    public void resolveNoPath() throws Exception {
        ResourceReference parentReference = mock(ResourceReference.class);
        ResourceReference newReference = mock(ResourceReference.class);

        when(newReference.getType()).thenReturn("blah");

        Optional<ResourceReference> result = underTest.resolve(parentReference, newReference);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolvePathAbsolute() throws Exception {
        String path = "path";
        ResourceReference parentReference = mock(ResourceReference.class);
        ResourceReference newReference = mock(ResourceReference.class);

        types.add("blah");
        when(newReference.getPath()).thenReturn(path);
        when(parentReference.getType()).thenReturn("blah");
        when(pathResolver.isRelative(path)).thenReturn(false);

        Optional<ResourceReference> result = underTest.resolve(parentReference, newReference);

        assertThat(result.get(), is(newReference));
    }

    @Test
    public void resolvePathRelative() throws Exception {
        String newPath = "path";
        String parentPath = "parentPath";
        String resolved = "resolved";
        ResourceReference parentReference = mock(ResourceReference.class);
        ResourceReference newReference = mock(ResourceReference.class);

        types.add("blah");
        when(newReference.getPath()).thenReturn(newPath);
        when(parentReference.getType()).thenReturn("blah");
        when(parentReference.getPath()).thenReturn(parentPath);
        when(pathResolver.isRelative(newPath)).thenReturn(true);
        when(pathResolver.resolve(parentPath, newPath)).thenReturn(resolved);

        Optional<ResourceReference> result = underTest.resolve(parentReference, newReference);

        assertThat(result.get().getPath(), is(resolved));
    }
}