package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.property.method.MethodPropertyExtractor;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethod;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MethodParameterResolverTest {
    private final MethodPropertyExtractor methodPropertyExtractor = mock(MethodPropertyExtractor.class);
    private final PropertyResolveRequest request = mock(PropertyResolveRequest.class, RETURNS_DEEP_STUBS);
    private MethodPropertyResolver underTest = new MethodPropertyResolver(methodPropertyExtractor);

    @Test
    public void resolveWhenNoDeclaredMethods() throws Exception {
        when(request.getEntity().type().methods()).thenReturn(Collections.<JavaMethod>emptyList());

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenExtractorDoesNotWorkout() throws Exception {
        JavaMethod javaMethod = mock(JavaMethod.class);
        when(request.getEntity().type().methods()).thenReturn(Collections.singletonList(javaMethod));
        when(methodPropertyExtractor.extract(request, javaMethod)).thenReturn(Optional.<Value>absent());

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenExtractorDoesWorksOut() throws Exception {
        Value value = mock(Value.class);
        JavaMethod javaMethod = mock(JavaMethod.class);
        when(request.getEntity().type().methods()).thenReturn(Collections.singletonList(javaMethod));
        when(methodPropertyExtractor.extract(request, javaMethod)).thenReturn(Optional.of(value));

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(value));
    }
}