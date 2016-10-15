package org.jtwig.property;

import com.google.common.base.Optional;
import org.hamcrest.CoreMatchers;
import org.jtwig.reflection.model.Value;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapPropertyResolverTest {
    private final PropertyResolveRequest request = mock(PropertyResolveRequest.class);
    private MapPropertyResolver underTest = new MapPropertyResolver();

    @Test
    public void resolveWithNullEntity() throws Exception {
        when(request.getEntity()).thenReturn(null);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), CoreMatchers.is(false));
    }

    @Test
    public void resolveWithNullValue() throws Exception {
        when(request.getEntity()).thenReturn(new Value(null));

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), CoreMatchers.is(false));
    }

    @Test
    public void resolveWhenWithArguments() throws Exception {
        when(request.getArguments()).thenReturn(asList(new Object()));

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenNoArgumentsButNotAMap() throws Exception {
        when(request.getArguments()).thenReturn(Collections.emptyList());
        when(request.getEntity()).thenReturn(new Value(1));

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenNoArgumentsAndMapButKeyNotFound() throws Exception {
        when(request.getArguments()).thenReturn(Collections.emptyList());
        when(request.getEntity()).thenReturn(new Value(new HashMap<>()));
        when(request.getPropertyName()).thenReturn("property");

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenNoArgumentsAndMapButKeyFound() throws Exception {
        final String property = "property";
        when(request.getArguments()).thenReturn(Collections.emptyList());
        when(request.getEntity()).thenReturn(new Value(new HashMap() {{
            put(property, "hello");
        }}));
        when(request.getPropertyName()).thenReturn(property);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals("hello", result.get().getValue());
    }
}