package org.jtwig.render.context.model;

import com.google.common.base.Optional;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EscapeModeTest {
    @Test
    public void fromStringNotFound() throws Exception {
        Optional<EscapeMode> result = EscapeMode.fromString("BLAH");

        assertEquals(false, result.isPresent());
    }
}