package org.jtwig.property.method;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.java.JavaMethodArgument;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RetrieveArgumentsService {
    private final static RetrieveArgumentsService INSTANCE = new RetrieveArgumentsService(new IsNullableType());

    public static RetrieveArgumentsService instance () {
        return INSTANCE;
    }

    private final IsNullableType isNullableType;

    public RetrieveArgumentsService(IsNullableType isNullableType) {
        this.isNullableType = isNullableType;
    }

    public Optional<List<Object>> retrieveArguments(List<Object> arguments, List<JavaMethodArgument> classes) {
        Iterator<Object> argumentsIterator = arguments.iterator();
        Iterator<JavaMethodArgument> methodArgumentIterator = classes.iterator();
        List<Object> result = new ArrayList<>();

        while (argumentsIterator.hasNext()) {
            Object next = argumentsIterator.next();
            JavaMethodArgument argument = methodArgumentIterator.next();

            if ((next != null)) {
                if (argument.type().isAssignableFrom(next.getClass())) {
                    result.add(next);
                } else {
                    return Optional.absent();
                }
            } else {
                if (!isNullableType.isNullable(argument.type())) {
                    return Optional.absent();
                } else {
                    result.add(null);
                }
            }
        }

        return Optional.of(result);
    }
}
