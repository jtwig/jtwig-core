package org.jtwig.functions.resolver;

import com.google.common.base.Supplier;
import org.jtwig.reflection.MethodInvokerBuilder;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.resolver.argument.ArgumentResolver;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.SimpleFunction;
import org.jtwig.functions.extract.BeanFunctionReferenceExtractor;
import org.jtwig.functions.reference.FunctionReference;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FunctionResolverBuilderTest {
    private Map<String, Collection<FunctionReference>> functions = new HashMap<>();
    private BeanFunctionReferenceExtractor extractor = mock(BeanFunctionReferenceExtractor.class);
    private MethodInvokerBuilder<FunctionArgument> methodInvoker = mock(MethodInvokerBuilder.class);
    private FunctionResolverBuilder underTest = new FunctionResolverBuilder(functions, extractor, methodInvoker);

    @Test
    public void withArgumentResolver() throws Exception {
        ArgumentResolver argumentResolver = mock(ArgumentResolver.class);

        underTest.withArgumentResolver(argumentResolver);

        verify(methodInvoker).withArgumentResolver(argumentResolver);
    }

    @Test
    public void withArgumentResolvers() throws Exception {
        ArgumentResolver argumentResolver = mock(ArgumentResolver.class);
        List<ArgumentResolver> argumentResolvers = asList(argumentResolver);

        underTest.withArgumentResolvers(argumentResolvers);

        verify(methodInvoker).withArgumentResolvers(argumentResolvers);
    }

    @Test
    public void withConverter() throws Exception {
        Converter converter = mock(Converter.class);

        underTest.withConverter(converter);

        verify(methodInvoker).withConverter(converter);
    }

    @Test
    public void withConverters() throws Exception {
        Converter converter = mock(Converter.class);
        List<Converter> converters = asList(converter);

        underTest.withConverters(converters);

        verify(methodInvoker).withConverters(converters);
    }

    @Test
    public void includeBean() throws Exception {
        Object bean = new Object();
        String name = "name";
        FunctionReference functionReference = mock(FunctionReference.class);
        when(functionReference.name()).thenReturn(name);
        when(extractor.extract(eq(bean), any(Supplier.class))).thenReturn(asList(functionReference));

        underTest.include(bean);

        assertThat(functions.get(name), hasItem(functionReference));
    }

    @Test
    public void includeReference() throws Exception {
        String name = "name";
        FunctionReference functionReference = mock(FunctionReference.class);
        when(functionReference.name()).thenReturn(name);

        underTest.include(functionReference);

        assertThat(functions.get(name), hasItem(functionReference));
    }

    @Test
    public void includeSimpleFunction() throws Exception {
        String name = "name";
        SimpleFunction function = mock(SimpleFunction.class);
        when(function.name()).thenReturn(name);

        underTest.include(function);

        assertThat(functions.get(name).size(), is(1));
    }

    @Test
    public void build() throws Exception {
        FunctionResolver result = underTest.build();

        assertThat(result, instanceOf(CoreFunctionResolver.class));
        assertThat(result, notNullValue());
    }

    @Test
    public void newBuilder() throws Exception {

        FunctionResolver result = FunctionResolverBuilder.newBuilder().build();


        assertThat(result, instanceOf(CoreFunctionResolver.class));
        assertThat(result, notNullValue());
    }
}