package org.jtwig.functions.resolver.position.vararg;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.reflection.model.Value;

import java.lang.reflect.Array;
import java.util.List;

public class FunctionArgumentMerger {
    private final ArrayComponentExtractor arrayComponentExtractor;

    public FunctionArgumentMerger(ArrayComponentExtractor arrayComponentExtractor) {
        this.arrayComponentExtractor = arrayComponentExtractor;
    }

    public Optional<Value> merge (List<FunctionArgument> input) {
        if (input.isEmpty()) {
            return Optional.of(new Value(null));
        }
        Class<?> type = arrayComponentExtractor.extract(input);
        Object[] instance = (Object[]) Array.newInstance(type, input.size());
        for (int i = 0; i < input.size(); i++) {
            Optional<Value> optional = input.get(i)
                    .getValue().as(type);

            if (optional.isPresent()) {
                instance[i] = optional.get()
                        .getValue();
            } else {
                return Optional.absent();
            }
        }
        return Optional.of(new Value(instance));
    }
}
