package org.jtwig.property.method;

import com.google.common.base.Optional;
import org.jtwig.property.method.argument.ArgumentConverter;
import org.jtwig.property.method.argument.AssignableTypes;
import org.jtwig.property.method.argument.IsNativeType;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RetrieveArgumentsService {
    private final static RetrieveArgumentsService INSTANCE = new RetrieveArgumentsService(new ArgumentConverter(IsNativeType.instance(), new AssignableTypes(IsNativeType.instance())));

    public static RetrieveArgumentsService instance () {
        return INSTANCE;
    }

    private final ArgumentConverter argumentConverter;

    public RetrieveArgumentsService(ArgumentConverter argumentConverter) {
        this.argumentConverter = argumentConverter;
    }

    public Optional<List<Object>> retrieveArguments(List<Object> arguments, List<JavaMethodArgument> classes) {
        Iterator<Object> argumentsIterator = arguments.iterator();
        Iterator<JavaMethodArgument> methodArgumentIterator = classes.iterator();
        List<Object> result = new ArrayList<>();

        while (argumentsIterator.hasNext()) {
            Object next = argumentsIterator.next();
            JavaMethodArgument argument = methodArgumentIterator.next();

            Optional<Value> value = argumentConverter.convert(argument, next);
            if (!value.isPresent()) return Optional.absent();
            else {
                result.add(value.get().getValue());
            }
        }

        return Optional.of(result);
    }
}
