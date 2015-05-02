package org.jtwig.functions.resolver.position.vararg;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.util.JtwigValue;

import java.lang.reflect.Array;
import java.util.List;

public class FunctionArgumentMerger {
    private final ArrayComponentExtractor arrayComponentExtractor;

    public FunctionArgumentMerger(ArrayComponentExtractor arrayComponentExtractor) {
        this.arrayComponentExtractor = arrayComponentExtractor;
    }

    public FunctionArgument merge (List<FunctionArgument> input) {
        if (input.isEmpty()) {
            return new FunctionArgument(Optional.<String>absent(), new JtwigValue(null));
        }
        Class<?> type = arrayComponentExtractor.extract(input);
        Object[] instance = (Object[]) Array.newInstance(type, input.size());
        for (int i = 0; i < input.size(); i++) {
            instance[i] = input.get(i).getValue().asObject();
        }
        return new FunctionArgument(Optional.<String>absent(), new JtwigValue(instance));
    }
}
