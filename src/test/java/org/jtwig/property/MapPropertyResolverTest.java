package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.reflection.model.Value;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapPropertyResolverTest {
    private final PropertyResolveRequest request = mock(PropertyResolveRequest.class);
    private MapPropertyResolver underTest = new MapPropertyResolver();

    @Test
    public void resolveWhenWithArguments() throws Exception {
        when(request.getArguments()).thenReturn(asList(mock(FunctionArgument.class)));

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenNoArgumentsButNotAMap() throws Exception {
        when(request.getArguments()).thenReturn(Collections.<FunctionArgument>emptyList());
        when(request.getEntity()).thenReturn(new Value(1));

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenNoArgumentsAndMapButKeyNotFound() throws Exception {
        when(request.getArguments()).thenReturn(Collections.<FunctionArgument>emptyList());
        when(request.getEntity()).thenReturn(new Value(new HashMap<>()));
        when(request.getPropertyName()).thenReturn("property");

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenNoArgumentsAndMapButKeyFound() throws Exception {
        final String property = "property";
        when(request.getArguments()).thenReturn(Collections.<FunctionArgument>emptyList());
        when(request.getEntity()).thenReturn(new Value(new HashMap() {{
            put(property, "hello");
        }}));
        when(request.getPropertyName()).thenReturn(property);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals("hello", result.get().getValue());
    }
}