package org.jtwig.property.strategy.method.argument.group;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.java.JavaMethod;
import org.jtwig.reflection.model.java.JavaMethodArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupingArgumentsService {
    public Optional<List<ArgumentGroup>> groupArguments(JavaMethod method, Object[] arguments) {
        if (method.isVarArgs()) {
            if (arguments.length >= method.numberOfArguments() - 1) {
                return Optional.of(groupVarArgs(method.arguments(), arguments));
            }
        } else {
            if (arguments.length == method.numberOfArguments()) {
                return Optional.of(groupSimpleArgs(method.arguments(), arguments, arguments.length));
            }
        }

        return Optional.absent();
    }

    private List<ArgumentGroup> groupSimpleArgs(List<JavaMethodArgument> javaMethodArguments, Object[] arguments, int max) {
        List<ArgumentGroup> groups = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            JavaMethodArgument javaMethodArgument = javaMethodArguments.get(i);
            groups.add(new SingleArgumentGroup(javaMethodArgument, arguments[i]));
        }
        return groups;
    }

    private List<ArgumentGroup> groupVarArgs(List<JavaMethodArgument> argumentList, Object[] arguments) {
        int max = argumentList.size() - 1;
        List<ArgumentGroup> result = groupSimpleArgs(argumentList, arguments, max);
        result.add(new VarArgumentGroup(argumentList.get(max), Arrays.asList(arguments).subList(max, arguments.length)));
        return result;
    }
}
