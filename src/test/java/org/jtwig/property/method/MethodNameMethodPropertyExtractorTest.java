package org.jtwig.property.method;

import com.google.common.base.Optional;
import org.jtwig.property.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethod;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MethodNameMethodPropertyExtractorTest {
    private final Comparator methodNameComparator = mock(Comparator.class);
    private final JavaMethod javaMethod = mock(JavaMethod.class);
    private final PropertyResolveRequest propertyResolveRequest = mock(PropertyResolveRequest.class, RETURNS_DEEP_STUBS);
    private final RetrieveArgumentsService retrieveArgumentsService = mock(RetrieveArgumentsService.class);
    private final MethodNameMethodPropertyExtractor underTest = new MethodNameMethodPropertyExtractor(retrieveArgumentsService, methodNameComparator);

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
    public void extractWhenArgumentsDifferentSize() throws Exception {
        String methodName = "parameter";
        String providedParameterName = "argument";
        List<JavaMethodArgument> javaMethodArguments = Collections.singletonList(mock(JavaMethodArgument.class));
        List<Object> arguments = asList(new Object(), new Object());

        when(propertyResolveRequest.getPropertyName()).thenReturn(providedParameterName);
        when(javaMethod.name()).thenReturn(methodName);
        when(javaMethod.arguments()).thenReturn(javaMethodArguments);
        when(propertyResolveRequest.getArguments()).thenReturn(arguments);
        when(methodNameComparator.compare(methodName, providedParameterName)).thenReturn(0);

        Optional<Value> result = underTest.extract(propertyResolveRequest, javaMethod);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractWhenArgumentsNotMatch() throws Exception {
        String methodName = "parameter";
        String providedParameterName = "argument";
        List<JavaMethodArgument> javaMethodArguments = Collections.singletonList(mock(JavaMethodArgument.class));
        List<Object> arguments = Collections.singletonList(new Object());

        when(propertyResolveRequest.getPropertyName()).thenReturn(providedParameterName);
        when(javaMethod.name()).thenReturn(methodName);
        when(javaMethod.arguments()).thenReturn(javaMethodArguments);
        when(propertyResolveRequest.getArguments()).thenReturn(arguments);
        when(methodNameComparator.compare(methodName, providedParameterName)).thenReturn(0);
        when(retrieveArgumentsService.retrieveArguments(arguments, javaMethodArguments)).thenReturn(Optional.<List<Object>>absent());

        Optional<Value> result = underTest.extract(propertyResolveRequest, javaMethod);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void extractHappy() throws Exception {
        String methodName = "parameter";
        String providedParameterName = "argument";
        List<JavaMethodArgument> javaMethodArguments = Collections.singletonList(mock(JavaMethodArgument.class));
        List<Object> arguments = Collections.singletonList(new Object());
        List<Object> resultArguments = asList(new Object());
        Object entity = new Object();
        Object expected = new Object();

        when(propertyResolveRequest.getPropertyName()).thenReturn(providedParameterName);
        when(javaMethod.name()).thenReturn(methodName);
        when(javaMethod.arguments()).thenReturn(javaMethodArguments);
        when(propertyResolveRequest.getArguments()).thenReturn(arguments);
        when(propertyResolveRequest.getEntity()).thenReturn(new Value(entity));
        when(methodNameComparator.compare(methodName, providedParameterName)).thenReturn(0);
        when(retrieveArgumentsService.retrieveArguments(arguments, javaMethodArguments)).thenReturn(Optional.of(resultArguments));
        when(javaMethod.invoke(entity, resultArguments.toArray())).thenReturn(expected);

        Optional<Value> result = underTest.extract(propertyResolveRequest, javaMethod);

        assertThat(result.isPresent(), is(true));
        assertSame(expected, result.get().getValue());
    }

    @Test
    public void thrownInvocationTargetException() throws Exception {
        String methodName = "parameter";
        String providedParameterName = "argument";
        List<JavaMethodArgument> javaMethodArguments = Collections.singletonList(mock(JavaMethodArgument.class));
        List<Object> arguments = Collections.singletonList(new Object());
        List<Object> resultArguments = asList(new Object());
        Object entity = new Object();
        Object expected = new Object();

        when(propertyResolveRequest.getPropertyName()).thenReturn(providedParameterName);
        when(javaMethod.name()).thenReturn(methodName);
        when(javaMethod.arguments()).thenReturn(javaMethodArguments);
        when(propertyResolveRequest.getArguments()).thenReturn(arguments);
        when(propertyResolveRequest.getEntity()).thenReturn(new Value(entity));
        when(methodNameComparator.compare(methodName, providedParameterName)).thenReturn(0);
        when(retrieveArgumentsService.retrieveArguments(arguments, javaMethodArguments)).thenReturn(Optional.of(resultArguments));
        when(javaMethod.invoke(entity, resultArguments.toArray())).thenThrow(InvocationTargetException.class);

        Optional<Value> result = underTest.extract(propertyResolveRequest, javaMethod);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void thrownIllegalAccessException() throws Exception {
        String methodName = "parameter";
        String providedParameterName = "argument";
        List<JavaMethodArgument> javaMethodArguments = Collections.singletonList(mock(JavaMethodArgument.class));
        List<Object> arguments = Collections.singletonList(new Object());
        List<Object> resultArguments = asList(new Object());
        Object entity = new Object();
        Object expected = new Object();

        when(propertyResolveRequest.getPropertyName()).thenReturn(providedParameterName);
        when(javaMethod.name()).thenReturn(methodName);
        when(javaMethod.arguments()).thenReturn(javaMethodArguments);
        when(propertyResolveRequest.getArguments()).thenReturn(arguments);
        when(propertyResolveRequest.getEntity()).thenReturn(new Value(entity));
        when(methodNameComparator.compare(methodName, providedParameterName)).thenReturn(0);
        when(retrieveArgumentsService.retrieveArguments(arguments, javaMethodArguments)).thenReturn(Optional.of(resultArguments));
        when(javaMethod.invoke(entity, resultArguments.toArray())).thenThrow(IllegalAccessException.class);

        Optional<Value> result = underTest.extract(propertyResolveRequest, javaMethod);

        assertThat(result.isPresent(), is(false));
    }
}