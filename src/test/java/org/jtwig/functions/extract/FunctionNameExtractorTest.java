package org.jtwig.functions.extract;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.bean.BeanMethod;
import org.jtwig.functions.annotations.JtwigFunction;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FunctionNameExtractorTest {
    private FunctionNameExtractor underTest = new FunctionNameExtractor();

    @Test
    public void extractNameWhenNameNotAvailable() throws Exception {
        BeanMethod beanMethod = mock(BeanMethod.class, RETURNS_DEEP_STUBS);
        JtwigFunction jtwigFunction = mock(JtwigFunction.class);
        when(beanMethod.method().annotation(JtwigFunction.class)).thenReturn(Optional.of(jtwigFunction));
        when(jtwigFunction.value()).thenReturn("");
        when(beanMethod.method().name()).thenReturn("name");

        String result = underTest.extractName(beanMethod);

        assertThat(result, is("name"));
    }

    @Test
    public void extractNameWhenNameAvailable() throws Exception {
        BeanMethod beanMethod = mock(BeanMethod.class, RETURNS_DEEP_STUBS);
        JtwigFunction jtwigFunction = mock(JtwigFunction.class);
        when(beanMethod.method().annotation(JtwigFunction.class)).thenReturn(Optional.of(jtwigFunction));
        when(jtwigFunction.value()).thenReturn("blah");
        when(beanMethod.method().name()).thenReturn("name");

        String result = underTest.extractName(beanMethod);

        assertThat(result, is("blah"));
    }
}