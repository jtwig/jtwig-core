package org.jtwig.content.json;

import com.google.common.base.Function;
import com.google.common.base.Optional;

public interface JsonMapperProvider {
    boolean isFound ();
    Function<Object, String> jsonMapper ();
}
