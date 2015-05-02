package org.jtwig.functions.extract;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.jtwig.reflection.MethodInvoker;
import org.jtwig.reflection.extractor.BeanMethodExtractor;
import org.jtwig.reflection.model.bean.BeanMethod;
import org.jtwig.reflection.model.java.JavaMethod;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.reference.FunctionReference;

import java.util.ArrayList;
import java.util.Collection;

public class BeanFunctionReferenceExtractor {
    private final BeanMethodExtractor extractor;
    private final BeanMethodFunctionExtractor functionExtractor;

    public BeanFunctionReferenceExtractor(BeanMethodExtractor extractor, BeanMethodFunctionExtractor functionExtractor) {
        this.extractor = extractor;
        this.functionExtractor = functionExtractor;
    }

    public Collection<FunctionReference> extract(Object bean, Supplier<MethodInvoker<FunctionArgument>> methodInvoker) {
        Collection<FunctionReference> result = new ArrayList<>();
        Collection<BeanMethod> beanMethods = extractor.extract(bean, new Predicate<JavaMethod>() {
            @Override
            public boolean apply(JavaMethod input) {
                return input.annotation(JtwigFunction.class).isPresent();
            }
        });

        for (BeanMethod beanMethod : beanMethods) {
            result.addAll(functionExtractor.extract(beanMethod));
        }

        return result;
    }

}
