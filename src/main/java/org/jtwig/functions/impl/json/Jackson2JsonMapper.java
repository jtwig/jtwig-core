package org.jtwig.functions.impl.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import org.jtwig.exceptions.CalculationException;

import java.io.IOException;

public class Jackson2JsonMapper implements Function<Object, String> {
    private final ObjectMapper OBJECT_MAPPER;

    public Jackson2JsonMapper(ObjectMapper objectMapper) {
        this.OBJECT_MAPPER = objectMapper;
    }

    public Jackson2JsonMapper() {
        this.OBJECT_MAPPER = new ObjectMapper();
    }

    @Override
    public String apply(Object input) {
        try {
            return OBJECT_MAPPER.writeValueAsString(input);
        } catch (IOException e) {
            throw new CalculationException("Unable to obtain json from object", e);
        }
    }
}
