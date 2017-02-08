package org.jtwig.property.strategy.method.finder;

import com.google.common.base.Optional;
import org.jtwig.property.strategy.method.MethodArgumentsMatcher;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.reflection.model.java.JavaMethod;
import org.jtwig.reflection.model.java.JavaMethods;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ExactMethodNamePropertyMethodFinderTest {
    private final MethodArgumentsMatcher methodArgumentsMatcher = mock(MethodArgumentsMatcher.class);
    private ExactMethodNamePropertyMethodFinder underTest = new ExactMethodNamePropertyMethodFinder(methodArgumentsMatcher);

    @Test
    public void noArgumentMatches() throws Exception {
        JavaClass javaClass = mock(JavaClass.class);
        JavaMethods javaMethods = mock(JavaMethods.class);
        JavaMethod javaMethod = mock(JavaMethod.class);

        String identifier = "identifier";
        List<Object> arguments = asList(new Object());

        given(javaClass.method(identifier)).willReturn(javaMethods);
        given(javaMethods.getMethods()).willReturn(asList(javaMethod));
        given(methodArgumentsMatcher.matches(javaMethod, arguments)).willReturn(false);

        Optional<JavaMethod> result = underTest.find(javaClass, identifier, arguments);

        assertThat(result.isPresent(), is(false));
    }
}