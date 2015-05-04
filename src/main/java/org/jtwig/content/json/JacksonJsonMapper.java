package org.jtwig.content.json;

import com.google.common.base.Function;
import org.codehaus.jackson.map.ObjectMapper;
import org.jtwig.exceptions.CalculationException;

import java.io.IOException;

public class JacksonJsonMapper implements Function<Object, String> {
    private final ObjectMapper OBJECT_MAPPER;

    public JacksonJsonMapper(ObjectMapper OBJECT_MAPPER) {
        this.OBJECT_MAPPER = OBJECT_MAPPER;
    }

    public JacksonJsonMapper() {
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