package org.jtwig.property.resolver;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.reflection.model.java.JavaClassManager;
import org.jtwig.reflection.model.java.JavaMethod;
import org.jtwig.value.Undefined;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

public class CallMethodPropertyResolverTest {
    private final JavaMethod javaMethod = mock(JavaMethod.class);
    private final String argument = "argument";
    private CallMethodPropertyResolver underTest = new CallMethodPropertyResolver(javaMethod, argument);

    @Test
    public void callThrowsInvocationTargetException() throws Exception {
        Object context = new Object();
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getContext()).willReturn(context);
        given(javaMethod.invoke(eq(context), argThat(arrayHasItem(argument)))).willThrow(InvocationTargetException.class);

        Object result = underTest.resolve(request);

        assertEquals(Undefined.UNDEFINED, result);
    }

    @Test
    public void callThrowsIllegalAccess() throws Exception {
        Object context = new Object();
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getContext()).willReturn(context);
        given(javaMethod.invoke(eq(context), argThat(arrayHasItem(argument)))).willThrow(IllegalAccessException.class);

        Object result = underTest.resolve(request);

        assertEquals(Undefined.UNDEFINED, result);
    }
    @Test
    public void callThrowsIllegalArgumentException() throws Exception {
        Object context = new Object();
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getContext()).willReturn(context);
        given(javaMethod.invoke(eq(context), argThat(arrayHasItem(argument)))).willThrow(IllegalArgumentException.class);

        Object result = underTest.resolve(request);

        assertEquals(Undefined.UNDEFINED, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void IllegalArgumentException() throws Exception {
        JavaClass metadata = JavaClassManager.classManager().metadata(String.class);
        JavaMethod method = metadata.method("length").getMethod().get();
        method.invoke(new Object(), new Object[]{});
    }

    @Test
    public void contextNull() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getContext()).willReturn(null);

        Object result = underTest.resolve(request);

        assertEquals(Undefined.UNDEFINED, result);
    }

    private Matcher<Object[]> arrayHasItem(final Object input) {
        return new TypeSafeMatcher<Object[]>() {
            @Override
            protected boolean matchesSafely(Object[] item) {
                for (Object object : item) {
                    if (object.equals(input)) return true;
                }

                return false;
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }
}