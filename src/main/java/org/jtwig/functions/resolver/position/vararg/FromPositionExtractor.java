package org.jtwig.functions.resolver.position.vararg;

import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.functions.FunctionArgument;

import java.util.ArrayList;
import java.util.List;

public class FromPositionExtractor {
    public Optional<List<FunctionArgument>> extract(int position, InputParameterResolverContext<FunctionArgument> context) {
        List<FunctionArgument> result = new ArrayList<>();
        for (int i = position; i < context.size(); i++) {
            if (context.isUsed(i)) {
                return Optional.absent();
            } else {
                result.add(context.value(i));
                context.markAsUsed(i);
            }
        }
        return Optional.of(result);
    }
}
