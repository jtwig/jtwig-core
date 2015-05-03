package org.jtwig.functions.impl;

import com.google.common.base.Function;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.functions.impl.json.JsonMapperFactory;
import org.jtwig.util.OptionalUtils;

public class JsonMapperFunction {
    private Function<Object, String> jsonMapper;

    @JtwigFunction("json_encode")
    public String encode (@Parameter("value") Object value) {
        return jsonMapper().apply(value);
    }

    private Function<Object, String> jsonMapper() {
        if (jsonMapper == null) {
            JsonMapperFactory jsonMapperFactory = getRenderContext().configuration().jsonMapper();
            jsonMapper = jsonMapperFactory.create()
                    .or(OptionalUtils.<Function<Object, String>, CalculationException>throwException(new CalculationException(String.format("No json mapper available, have linked one of the provider libraries? %s", jsonMapperFactory.name()))));
        }
        return jsonMapper;
    }

    protected RenderContext getRenderContext () {
        return RenderContextHolder.get();
    }
}
