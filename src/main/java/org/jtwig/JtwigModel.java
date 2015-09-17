package org.jtwig;

import org.jtwig.context.values.SimpleValueContext;
import org.jtwig.reflection.model.Value;

import java.util.HashMap;

public class JtwigModel extends SimpleValueContext {
    public static JtwigModel newModel () {
        return new JtwigModel();
    }

    public JtwigModel() {
        super(new HashMap<String, Value>());
    }

    public JtwigModel with (String name, Object value) {
        super.add(name, value);
        return this;
    }
}
