package org.jtwig.functions.extract;

import com.google.common.base.Supplier;
import org.jtwig.reflection.MethodInvoker;
import org.jtwig.reflection.model.bean.BeanMethod;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.reference.AliasFunctionReference;
import org.jtwig.functions.reference.BeanFunctionReference;
import org.jtwig.functions.reference.FunctionReference;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.asList;

public class BeanMethodFunctionExtractor {
    private final Supplier<MethodInvoker<FunctionArgument>> methodInvoker;
    private final FunctionNameExtractor functionNameExtractor;

    public BeanMethodFunctionExtractor(Supplier<MethodInvoker<FunctionArgument>> methodInvoker,
                                       FunctionNameExtractor functionNameExtractor) {
        this.methodInvoker = methodInvoker;
        this.functionNameExtractor = functionNameExtractor;
    }

    public Collection<FunctionReference> extract (BeanMethod beanMethod) {
        Collection<FunctionReference> result = new ArrayList<>();
        FunctionReference beanFunctionReference = new BeanFunctionReference(functionNameExtractor.extractName(beanMethod), beanMethod, methodInvoker);
        result.add(beanFunctionReference);
        for (String alias : getAliases(beanMethod)) {
            if (StringUtils.isNotBlank(alias)) {
                result.add(new AliasFunctionReference(alias, beanFunctionReference));
            }
        }
        return result;
    }

    private Collection<String> getAliases(BeanMethod beanMethod) {
        return asList(beanMethod.method().annotation(JtwigFunction.class).get().aliases());
    }

}
