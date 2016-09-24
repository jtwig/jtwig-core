package org.jtwig;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import org.jtwig.reflection.model.Value;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JtwigModelTest {
    @Test
    public void newModelFromMap() throws Exception {

        JtwigModel underTest = JtwigModel.newModel(ImmutableMap.<String, Object>builder()
                .put("test", "hello")
                .build());

        Optional<Value> result = underTest.get("test");

        assertEquals("hello", result.get().getValue());
    }
}