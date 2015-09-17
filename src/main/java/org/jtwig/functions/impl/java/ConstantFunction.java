package org.jtwig.functions.impl.java;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.util.ClasspathFinder;
import org.jtwig.util.OptionalUtils;

import static org.jtwig.value.JtwigValueFactoryHolder.value;

public class ConstantFunction extends SimpleJtwigFunction {
    private final ClasspathFinder classpathFinder;

    public ConstantFunction(ClasspathFinder classpathFinder) {
        this.classpathFinder = classpathFinder;
    }

    @Override
    public String name() {
        return "constant";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request
                .minimumNumberOfArguments(1)
                .maximumNumberOfArguments(2);

        if (request.getNumberOfArguments() == 1) {
            return getConstant(request, request.getArgument(0, String.class));
        } else {
            return value(getConstant(request, request.getArgument(1, String.class))).isEqualTo(request.get(0));
        }
    }

    private Object getConstant(JtwigFunctionRequest request, String constant) {
        int constantNamePosition = constant.lastIndexOf(".");
        if (constantNamePosition == -1) {
            throw request.exception(String.format("Invalid constant specified '%s'", constant));
        }

        String className = constant.substring(0, constantNamePosition);
        String constantName = constant.substring(constantNamePosition + 1);

        return classpathFinder.load(className)
                .or(OptionalUtils.<JavaClass, CalculationException>throwException(request.exception(String.format("Class %s not found", className))))
                .constant(constantName)
                .or(OptionalUtils.<Value, CalculationException>throwException(request.exception(String.format("Class %s does not expose constant %s", className, constantName))))
                .getValue();
    }
}
