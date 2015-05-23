package org.jtwig.content.json;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeJsonMapperProviderTest {
    private Collection<JsonMapperProvider> factories = new ArrayList<>();
    private CompositeJsonMapperProvider underTest = new CompositeJsonMapperProvider(factories);

    @Before
    public void setUp() throws Exception {
        factories.clear();
    }

    @Test
    public void createWhenNotFound() throws Exception {
        JsonMapperProvider jsonMapperProvider = mock(JsonMapperProvider.class);
        when(jsonMapperProvider.isFound()).thenReturn(false);
        factories.add(jsonMapperProvider);

        Function<Object, String> result = underTest.jsonMapper();

        assertThat(result, nullValue());
    }

    @Test
    public void createWhenFound() throws Exception {
        JsonMapperProvider jsonMapperProvider = mock(JsonMapperProvider.class);
        Function function = mock(Function.class);
        when(jsonMapperProvider.isFound()).thenReturn(true);
        when(jsonMapperProvider.jsonMapper()).thenReturn(function);
        factories.add(jsonMapperProvider);

        Function<Object, String> result = underTest.jsonMapper();

        assertThat(result, notNullValue());
        assertThat(result, is(function));
    }
}