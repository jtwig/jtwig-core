package org.jtwig.functions.impl;

import com.google.common.base.Objects;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.util.ClasspathFinder;
import org.jtwig.util.OptionalUtils;

public class ConstantFunction {
    private final ClasspathFinder classpathFinder;

    public ConstantFunction(ClasspathFinder classpathFinder) {
        this.classpathFinder = classpathFinder;
    }

    @JtwigFunction("constant")
    public Object constant(@Parameter("constant") String constant) throws CalculationException {
        int constantNamePosition = constant.lastIndexOf(".");
        if (constantNamePosition == -1) {
            throw new CalculationException(String.format("Invalid constant specified '%s'", constant));
        }

        String className = constant.substring(0, constantNamePosition);
        String constantName = constant.substring(constantNamePosition + 1);

        return classpathFinder.load(className)
                .or(OptionalUtils.<JavaClass, CalculationException>throwException(new CalculationException(String.format("Class %s not found", className))))
                .constant(constantName)
                .or(OptionalUtils.<Value, CalculationException>throwException(new CalculationException(String.format("Class %s does not expose constant %s", className, constantName))))
                .getValue();
    }

    @JtwigFunction("constant")
    public boolean constantEquals (@Parameter("value") Object value, @Parameter("constant") String constant) {
        return Objects.equal(constant(constant), value);
    }
}
