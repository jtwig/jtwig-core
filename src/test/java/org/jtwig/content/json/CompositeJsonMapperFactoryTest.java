package org.jtwig.content.json;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeJsonMapperFactoryTest {
    private Collection<JsonMapperFactory> factories = new ArrayList<>();
    private CompositeJsonMapperFactory underTest = new CompositeJsonMapperFactory(factories);

    @Before
    public void setUp() throws Exception {
        factories.clear();
    }

    @Test
    public void createWhenNotFound() throws Exception {
        JsonMapperFactory jsonMapperFactory = mock(JsonMapperFactory.class);
        when(jsonMapperFactory.create()).thenReturn(Optional.<Function<Object,String>>absent());
        factories.add(jsonMapperFactory);

        Optional<Function<Object, String>> result = underTest.create();

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void createWhenFound() throws Exception {
        JsonMapperFactory jsonMapperFactory = mock(JsonMapperFactory.class);
        Function function = mock(Function.class);
        when(jsonMapperFactory.create()).thenReturn(Optional.<Function<Object,String>>of(function));
        factories.add(jsonMapperFactory);

        Optional<Function<Object, String>> result = underTest.create();

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(function));
    }

    @Test
    public void name() throws Exception {
        JsonMapperFactory jsonMapperFactory1 = mock(JsonMapperFactory.class);
        JsonMapperFactory jsonMapperFactory2 = mock(JsonMapperFactory.class);
        when(jsonMapperFactory1.name()).thenReturn("hello");
        when(jsonMapperFactory2.name()).thenReturn("hello2");
        factories.add(jsonMapperFactory1);
        factories.add(jsonMapperFactory2);

        String result = underTest.name();

        assertThat(result, is("hello, hello2"));
    }
}