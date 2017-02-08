package org.jtwig.functions.impl.java;

import com.google.common.base.Optional;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.reflection.model.java.JavaConstant;
import org.jtwig.util.ClasspathFinder;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

public class ConstantFunctionTest {
    ClasspathFinder classpathFinder = mock(ClasspathFinder.class);
    ConstantFunction underTest = new ConstantFunction(classpathFinder);

    @Test(expected = CalculationException.class)
    public void valueNotRetrieved() throws Exception {
        Object argument = new Object();
        String className = "nome";
        String variableName = "test";

        String argAsString = className + "." + variableName;

        FunctionRequest request = mock(FunctionRequest.class, RETURNS_DEEP_STUBS);
        JavaClass javaClass = mock(JavaClass.class);
        JavaConstant javaConstant = mock(JavaConstant.class);

        given(request.getNumberOfArguments()).willReturn(1);
        given(request.get(0)).willReturn(argument);
        given(request.getEnvironment().getValueEnvironment().getStringConverter().convert(argument)).willReturn(argAsString);
        given(classpathFinder.load(className)).willReturn(Optional.of(javaClass));
        given(javaClass.constant(variableName)).willReturn(Optional.of(javaConstant));
        given(javaConstant.value()).willReturn(Optional.<Value>absent());
        given(request.exception(String.format("Unable to retrieve value of constant %s in class %s", className, variableName))).willReturn(new CalculationException("message"));

        underTest.execute(request);
    }
}