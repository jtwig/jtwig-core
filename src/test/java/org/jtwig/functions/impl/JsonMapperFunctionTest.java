package org.jtwig.functions.impl;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.context.RenderContext;
import org.jtwig.content.json.JsonMapperProvider;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JsonMapperFunctionTest {
    private final RenderContext renderContext = mock(RenderContext.class);
    private final Environment environment = mock(Environment.class);
    private final JsonMapperProvider jsonMapperProvider = mock(JsonMapperProvider.class);
    private final Function function = mock(Function.class);
    private JsonMapperFunction underTest = new JsonMapperFunction() {
        @Override
        protected RenderContext getRenderContext() {
            return renderContext;
        }
    };

    @Before
    public void setUp() throws Exception {
        when(renderContext.environment()).thenReturn(environment);
        when(environment.jsonMapper()).thenReturn(jsonMapperProvider);
    }

    @Test
    public void encodeReturnsValueOfFunctionExecution() throws Exception {
        when(jsonMapperProvider.jsonMapper()).thenReturn(function);
        when(jsonMapperProvider.isFound()).thenReturn(true);
        when(function.apply("blah")).thenReturn("hello");

        String result = underTest.encode("blah");

        assertThat(result, is("hello"));
    }

    @Test
    public void jsonEncodeFactoryCalledOnceWhenResolved() throws Exception {
        when(jsonMapperProvider.jsonMapper()).thenReturn(function);
        when(jsonMapperProvider.isFound()).thenReturn(true);

        underTest.encode("hello");
        underTest.encode("2");

        verify(jsonMapperProvider).isFound();
        verify(jsonMapperProvider).jsonMapper();
    }

    @Test
    public void jsonEncodeFactoryCalledEveryTimeWhenNotResolved() throws Exception {
        when(jsonMapperProvider.isFound()).thenReturn(false);

        try {
            underTest.encode("hello");
        } catch (Throwable e) {
            // ignore
        }
        try {
            underTest.encode("2");
        } catch (Throwable e) {
            // ignore
        }

        verify(jsonMapperProvider, times(2)).isFound();
    }
}