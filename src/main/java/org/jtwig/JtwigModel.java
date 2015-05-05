package org.jtwig;

import org.jtwig.context.values.SimpleValueContext;
import org.jtwig.value.JtwigValue;

import java.util.HashMap;

public class JtwigModel extends SimpleValueContext {
    public static JtwigModel newModel () {
        return new JtwigModel();
    }

    public JtwigModel() {
        super(new HashMap<String, JtwigValue>());
    }

    public JtwigModel with (String name, Object value) {
        super.add(name, value);
        return this;
    }
}
