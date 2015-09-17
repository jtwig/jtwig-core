package org.jtwig.value;

import org.jtwig.context.RenderContextHolder;

public class JtwigValueFactoryHolder {
    public static JtwigValue value (Object value) {
        return JtwigValueFactory.value(value, RenderContextHolder.get().environment().valueConfiguration());
    }
}
