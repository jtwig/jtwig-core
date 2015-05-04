package org.jtwig.functions.impl;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.configuration.Configuration;
import org.jtwig.context.RenderContext;
import org.jtwig.content.json.JsonMapperFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JsonMapperFunctionTest {
    private final RenderContext renderContext = mock(RenderContext.class);
    private final Configuration configuration = mock(Configuration.class);
    private final JsonMapperFactory jsonMapperFactory = mock(JsonMapperFactory.class);
    private final Function function = mock(Function.class);
    private JsonMapperFunction underTest = new JsonMapperFunction() {
        @Override
        protected RenderContext getRenderContext() {
            return renderContext;
        }
    };

    @Before
    public void setUp() throws Exception {
        when(renderContext.configuration()).thenReturn(configuration);
        when(configuration.jsonMapper()).thenReturn(jsonMapperFactory);
    }

    @Test
    public void encodeReturnsValueOfFunctionExecution() throws Exception {
        when(jsonMapperFactory.create()).thenReturn(Optional.<Function<Object,String>>of(function));
        when(function.apply("blah")).thenReturn("hello");

        String result = underTest.encode("blah");

        assertThat(result, is("hello"));
    }

    @Test
    public void jsonEncodeFactoryCalledOnceWhenResolved() throws Exception {
        when(jsonMapperFactory.create()).thenReturn(Optional.<Function<Object,String>>of(function));

        underTest.encode("hello");
        underTest.encode("2");

        verify(jsonMapperFactory).create();
    }

    @Test
    public void jsonEncodeFactoryCalledEveryTimeWhenNotResolved() throws Exception {
        when(jsonMapperFactory.create()).thenReturn(Optional.<Function<Object,String>>absent());

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

        verify(jsonMapperFactory, times(2)).create();
    }
}