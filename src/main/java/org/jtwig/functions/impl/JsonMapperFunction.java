package org.jtwig.functions.impl;

import com.google.common.base.Function;
import org.jtwig.content.json.JsonMapperProvider;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;

public class JsonMapperFunction {
    private Function<Object, String> jsonMapper;

    @JtwigFunction("json_encode")
    public String encode (@Parameter("value") Object value) {
        return jsonMapper().apply(value);
    }

    private Function<Object, String> jsonMapper() {
        if (jsonMapper == null) {
            JsonMapperProvider jsonMapperProvider = getRenderContext().environment().jsonMapper();
            if (!jsonMapperProvider.isFound()) {
                throw new CalculationException(String.format("No json mapper available, have linked one of the provider libraries?"));
            } else {
                jsonMapper = jsonMapperProvider.jsonMapper();
            }
        }
        return jsonMapper;
    }

    protected RenderContext getRenderContext () {
        return RenderContextHolder.get();
    }
}
