package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CompositeConfigurationParameterResolverTest {

    private final ArrayList<PropertyResolver> resolvers = new ArrayList<>();
    private CompositePropertyResolver underTest = new CompositePropertyResolver(resolvers);

    @Before
    public void setUp() throws Exception {
        resolvers.clear();
    }

    @Test
    public void resolveWhenNoResolvers() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);
        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenResolversNotResolving() throws Exception {
        PropertyResolver propertyResolver = mock(PropertyResolver.class);
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);
        when(propertyResolver.resolve(any(PropertyResolveRequest.class))).thenReturn(Optional.<JtwigValue>absent());
        resolvers.add(propertyResolver);

        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
        verify(propertyResolver).resolve(request);
    }

    @Test
    public void resolveWhenResolversResolving() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);
        PropertyResolver propertyResolver = mock(PropertyResolver.class);
        when(propertyResolver.resolve(request)).thenReturn(Optional.of(JtwigValueFactory.create("hi")));
        resolvers.add(propertyResolver);

        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().asString(), is("hi"));
    }
}