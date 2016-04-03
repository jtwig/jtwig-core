package org.jtwig.value.context;

import com.google.common.base.Optional;
import org.jtwig.JtwigModel;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.Undefined;

public class JtwigModelValueContext implements ValueContext {
    private final JtwigModel jtwigModel;

    public JtwigModelValueContext(JtwigModel jtwigModel) {
        this.jtwigModel = jtwigModel;
    }

    @Override
    public Object resolve(String key) {
        Optional<Value> result = jtwigModel.get(key);
        if (result.isPresent()) {
            return result.get().getValue();
        } else {
            return Undefined.UNDEFINED;
        }
    }

    @Override
    public ValueContext with(String key, Object value) {
        throw new IllegalArgumentException("JtwigModelValueContext cannot implement such write operation as it is readonly. Tip: Wrap it inside a modifiable value context");
    }
}
