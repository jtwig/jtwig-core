package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.context.values.SimpleValueContext;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;
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
        when(request.getArguments()).thenReturn(asList(mock(JtwigValue.class)));

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
        HashMap<String, Value> source = new HashMap<>();
        when(request.getEntity()).thenReturn(new Value(new SimpleValueContext(source)));
        when(request.getPropertyName()).thenReturn("test");

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenValueValueContextFindValue() throws Exception {
        HashMap<String, Value> source = new HashMap<>();
        source.put("test", new Value("value"));
        when(request.getEntity()).thenReturn(new Value(new SimpleValueContext(source)));
        when(request.getPropertyName()).thenReturn("test");

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals(result.get().getValue(), "value");
    }
}