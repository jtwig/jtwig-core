package org.jtwig.property.resolver;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.reflection.model.java.JavaClassManager;
import org.jtwig.reflection.model.java.JavaField;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FieldPropertyResolverTest {
    private final JavaField javaField = mock(JavaField.class);
    private FieldPropertyResolver underTest = new FieldPropertyResolver(javaField);

    @Test
    public void fieldAccessThrowsIllegalAccess() throws Exception {
        Object context = new Object();
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getContext()).willReturn(context);
        given(javaField.value(context)).willThrow(IllegalAccessException.class);

        Optional<Value> result = underTest.resolve(request);

        assertEquals(Optional.<Value>absent(), result);
    }

    @Test
    public void fieldAccessThrowsIllegalArgumentException() throws Exception {
        Object context = new Object();
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getContext()).willReturn(context);
        given(javaField.value(context)).willThrow(IllegalArgumentException.class);

        Optional<Value> result = underTest.resolve(request);

        assertEquals(Optional.<Value>absent(), result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void IllegalArgumentException() throws Exception {
        JavaClass metadata = JavaClassManager.classManager().metadata(TestBean.class);
        JavaField javaField = metadata.field("property").get();
        javaField.value(new Object());
    }

    public static class TestBean {
        private final String property;

        public TestBean(String property) {
            this.property = property;
        }
    }
}