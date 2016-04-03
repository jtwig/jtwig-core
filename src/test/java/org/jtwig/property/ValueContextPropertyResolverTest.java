package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.context.MapValueContext;
import org.junit.Test;

import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValueContextPropertyResolverTest {
    private ValueContextPropertyResolver underTest = new ValueContextPropertyResolver();
    private PropertyResolveRequest request = mock(PropertyResolveRequest.class);

    @Test
    public void resolveWithArguments() throws Exception {
        when(request.getArguments()).thenReturn(asList(new Object()));

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenValueNull() throws Exception {
        when(request.getEntity()).thenReturn(new Value(null));

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenValueNotValueContext() throws Exception {
        when(request.getEntity()).thenReturn(new Value(""));

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenValueValueContextNotFindValue() throws Exception {
        HashMap<String, Object> source = new HashMap<>();
        when(request.getPropertyName()).thenReturn("test");
        when(request.getEntity()).thenReturn(new Value(new MapValueContext(source)));

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenValueValueContextFindValue() throws Exception {
        HashMap<String, Object> source = new HashMap<>();
        source.put("test", "value");
        when(request.getPropertyName()).thenReturn("test");
        when(request.getEntity()).thenReturn(new Value(new MapValueContext(source)));

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals(result.get().getValue(), "value");
    }
}