package org.jtwig.functions.resolver.position;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.input.InputParameterResolverContext;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.resolver.position.vararg.FromPositionExtractor;
import org.jtwig.functions.resolver.position.vararg.FunctionArgumentMerger;
import org.jtwig.util.JtwigValue;

import java.util.List;

public class VarArgsPositionParameterResolver implements PositionParameterResolver {
    private final FunctionArgumentMerger functionArgumentMerger;
    private final FromPositionExtractor fromPositionExtractor;

    public VarArgsPositionParameterResolver(FunctionArgumentMerger functionArgumentMerger, FromPositionExtractor fromPositionExtractor) {
        this.functionArgumentMerger = functionArgumentMerger;
        this.fromPositionExtractor = fromPositionExtractor;
    }

    @Override
    public Optional<FunctionArgument> resolve(JavaMethodArgument javaMethodArgument, int position, InputParameterResolverContext<FunctionArgument> context) {
        if (javaMethodArgument.isVarArg()) {
            if (context.size() <= position) return Optional.of(new FunctionArgument(Optional.<String>absent(), new JtwigValue(null)));
            return fromPositionExtractor
                    .extract(position, context)
                    .transform(getFunction());
        } else {
            return Optional.absent();
        }
    }

    private Function<List<FunctionArgument>, FunctionArgument> getFunction() {
        return new Function<List<FunctionArgument>, FunctionArgument>() {
            @Override
            public FunctionArgument apply(List<FunctionArgument> input) {
                return functionArgumentMerger.merge(input);
            }
        };
    }
}
