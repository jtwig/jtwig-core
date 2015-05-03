package org.jtwig.functions.impl.json;

import com.google.common.base.Function;
import com.google.common.base.Optional;

public interface JsonMapperFactory {
    Optional<Function<Object, String>> create ();
    String name();
}
