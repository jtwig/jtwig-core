package org.jtwig.property;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Collections2;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.util.JtwigValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;

public class MethodPropertyResolver implements PropertyResolver {
    private static Logger logger = LoggerFactory.getLogger(MethodPropertyResolver.class);
    public static Comparator<String> exactlyEqual () {
        return new Comparator<String>() {
            @Override
            public int compare(String methodName, String searchingName) {
                return methodName.compareTo(searchingName);
            }
        };
    }

    public static Comparator<String> prefixedEqual (final String prefix) {
        return new Comparator<String>() {
            @Override
            public int compare(String methodName, String searchingName) {
                return methodName.compareTo(prefix + StringUtils.capitalize(searchingName));
            }
        };
    }

    private final Comparator<String> methodNameComparator;

    public MethodPropertyResolver(Comparator<String> methodNameComparator) {
        this.methodNameComparator = methodNameComparator;
    }

    @Override
    public Optional<JtwigValue> resolve(PropertyResolveRequest request) {
        Method[] declaredMethods = request.getEntity().getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (methodNameComparator.compare(declaredMethod.getName(), request.getPropertyName()) == 0) {
                if (request.getArguments().size() == declaredMethod.getParameterTypes().length) {
                    Collection<Object> arguments = extractValues(request.getArguments());
                    if (argumentsAreAssignable(arguments, asList(declaredMethod.getParameterTypes()))) {
                        try {
                            return Optional.of(new JtwigValue(declaredMethod.invoke(request.getEntity(), arguments.toArray())));
                        } catch (InvocationTargetException | IllegalAccessException e) {
                            logger.debug(ErrorMessageFormatter.errorMessage(request.getPosition(), String.format("Unable to execute method '%s' on '%s'", request.getPropertyName(), request.getEntity())), e);
                        }
                    }
                }
            }
        }

        return Optional.absent();
    }

    private Collection<Object> extractValues(Collection<FunctionArgument> arguments) {
        return Collections2.transform(arguments, new Function<FunctionArgument, Object>() {
            @Override
            public Object apply(FunctionArgument input) {
                return input.getValue().asObject();
            }
        });
    }

    private boolean argumentsAreAssignable(Collection<Object> arguments, List<Class<?>> classes) {
        Iterator<Object> argumentsIterator = arguments.iterator();
        Iterator<Class<?>> classesIterator = classes.iterator();

        while (argumentsIterator.hasNext()) {
            Object next = argumentsIterator.next();
            Class<?> type = classesIterator.next();

            if ((next != null) && !type.isAssignableFrom(next.getClass())) {
                return false;
            }
        }

        return true;
    }

}
