package org.jtwig.property.method;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.property.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethod;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MethodNameMethodPropertyExtractorTest {
    private final Comparator methodNameComparator = mock(Comparator.class);
    private final JavaMethod javaMethod = mock(JavaMethod.class);
    private final PropertyResolveRequest propertyResolveRequest = mock(PropertyResolveRequest.class, RETURNS_DEEP_STUBS);
    private final MethodNameMethodPropertyExtractor underTest = new MethodNameMethodPropertyExtractor(methodNameComparator);

    @Test
    public void extractWhenMethodNameComparatorDoesNotMatch() throws Exception {
        String methodName = "parameter";
        String providedParameterName = "argument";
        when(propertyResolveRequest.getPropertyName()).thenReturn(providedParameterName);
        when(javaMethod.name()).thenReturn(methodName);
        when(methodNameComparator.compare(methodName, providedParameterName)).thenReturn(1);

        Optional<Value> result = underTest.extract(propertyResolveRequest, javaMethod);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractWhenDifferentNumberOfArguments() throws Exception {
        String methodName = "parameter";
        String providedParameterName = "argument";
        when(propertyResolveRequest.getPropertyName()).thenReturn(providedParameterName);
        when(javaMethod.name()).thenReturn(methodName);
        when(methodNameComparator.compare(methodName, providedParameterName)).thenReturn(0);

        when(propertyResolveRequest.getArguments()).thenReturn(Collections.<FunctionArgument>emptyList());
        when(javaMethod.arguments()).thenReturn(Collections.singletonList(mock(JavaMethodArgument.class)));

        Optional<Value> result = underTest.extract(propertyResolveRequest, javaMethod);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractWhenArgumentTypesNotAssignable() throws Exception {
        String methodName = "parameter";
        String providedParameterName = "argument";
        when(propertyResolveRequest.getPropertyName()).thenReturn(providedParameterName);
        when(javaMethod.name()).thenReturn(methodName);
        when(methodNameComparator.compare(methodName, providedParameterName)).thenReturn(0);

        FunctionArgument givenArgument = mock(FunctionArgument.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(propertyResolveRequest.getArguments()).thenReturn(Collections.singletonList(givenArgument));
        when(javaMethod.arguments()).thenReturn(Collections.singletonList(javaMethodArgument));

        when(givenArgument.getValue()).thenReturn("");
        when(javaMethodArgument.type()).thenReturn(Integer.class);

        Optional<Value> result = underTest.extract(propertyResolveRequest, javaMethod);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractWhenInvocationTargetException() throws Exception {
        String methodName = "parameter";
        String providedParameterName = "argument";
        when(propertyResolveRequest.getPropertyName()).thenReturn(providedParameterName);
        when(javaMethod.name()).thenReturn(methodName);
        when(methodNameComparator.compare(methodName, providedParameterName)).thenReturn(0);

        FunctionArgument givenArgument = mock(FunctionArgument.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(propertyResolveRequest.getArguments()).thenReturn(Collections.singletonList(givenArgument));
        when(javaMethod.arguments()).thenReturn(Collections.singletonList(javaMethodArgument));

        when(givenArgument.getValue()).thenReturn("");
        when(javaMethodArgument.type()).thenReturn(String.class);

        Object context = new Object();
        when(propertyResolveRequest.getEntity().getValue()).thenReturn(context);
        when(javaMethod.invoke(context, new Object[]{""})).thenThrow(InvocationTargetException.class);

        Optional<Value> result = underTest.extract(propertyResolveRequest, javaMethod);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractWhenIllegalAccessException() throws Exception {
        String methodName = "parameter";
        String providedParameterName = "argument";
        when(propertyResolveRequest.getPropertyName()).thenReturn(providedParameterName);
        when(javaMethod.name()).thenReturn(methodName);
        when(methodNameComparator.compare(methodName, providedParameterName)).thenReturn(0);

        FunctionArgument givenArgument = mock(FunctionArgument.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(propertyResolveRequest.getArguments()).thenReturn(Collections.singletonList(givenArgument));
        when(javaMethod.arguments()).thenReturn(Collections.singletonList(javaMethodArgument));

        when(givenArgument.getValue()).thenReturn("");
        when(javaMethodArgument.type()).thenReturn(String.class);

        Object context = new Object();
        when(propertyResolveRequest.getEntity().getValue()).thenReturn(context);
        when(javaMethod.invoke(context, new Object[] {""})).thenThrow(IllegalAccessException.class);

        Optional<Value> result = underTest.extract(propertyResolveRequest, javaMethod);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractHappyPath() throws Exception {
        String methodName = "parameter";
        String providedParameterName = "argument";
        when(propertyResolveRequest.getPropertyName()).thenReturn(providedParameterName);
        when(javaMethod.name()).thenReturn(methodName);
        when(methodNameComparator.compare(methodName, providedParameterName)).thenReturn(0);

        FunctionArgument givenArgument = mock(FunctionArgument.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(propertyResolveRequest.getArguments()).thenReturn(Collections.singletonList(givenArgument));
        when(javaMethod.arguments()).thenReturn(Collections.singletonList(javaMethodArgument));

        when(givenArgument.getValue()).thenReturn("");
        when(javaMethodArgument.type()).thenReturn(String.class);

        Object context = new Object();
        Object methodInvocationResult = new Object();
        when(propertyResolveRequest.getEntity().getValue()).thenReturn(context);
        when(javaMethod.invoke(context, new Object[] {""})).thenReturn(methodInvocationResult);

        Optional<Value> result = underTest.extract(propertyResolveRequest, javaMethod);

        assertThat(result.isPresent(), is(true));
        assertSame(result.get().getValue(), methodInvocationResult);
    }

    @Test
    public void extractNullAlwaysAssignable() throws Exception {
        String methodName = "parameter";
        String providedParameterName = "argument";
        when(propertyResolveRequest.getPropertyName()).thenReturn(providedParameterName);
        when(javaMethod.name()).thenReturn(methodName);
        when(methodNameComparator.compare(methodName, providedParameterName)).thenReturn(0);

        FunctionArgument givenArgument = mock(FunctionArgument.class);
        JavaMethodArgument javaMethodArgument = mock(JavaMethodArgument.class);
        when(propertyResolveRequest.getArguments()).thenReturn(Collections.singletonList(givenArgument));
        when(javaMethod.arguments()).thenReturn(Collections.singletonList(javaMethodArgument));

        when(givenArgument.getValue()).thenReturn(null);
        when(javaMethodArgument.type()).thenReturn(String.class);

        Object context = new Object();
        Object methodInvocationResult = new Object();
        when(propertyResolveRequest.getEntity().getValue()).thenReturn(context);
        when(javaMethod.invoke(context, new Object[] {null})).thenReturn(methodInvocationResult);

        Optional<Value> result = underTest.extract(propertyResolveRequest, javaMethod);

        assertThat(result.isPresent(), is(true));
        assertSame(result.get().getValue(), methodInvocationResult);
    }
}