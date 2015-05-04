package org.jtwig.content.json;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Collections2;
import org.parboiled.common.StringUtils;

import java.util.Collection;

public class CompositeJsonMapperFactory implements JsonMapperFactory {
    private final Collection<JsonMapperFactory> jsonMapperFactories;

    public CompositeJsonMapperFactory(Collection<JsonMapperFactory> jsonMapperFactories) {
        this.jsonMapperFactories = jsonMapperFactories;
    }

    @Override
    public Optional<Function<Object, String>> create() {
        for (JsonMapperFactory jsonMapperFactory : jsonMapperFactories) {
            Optional<Function<Object, String>> result = jsonMapperFactory.create();
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.absent();
    }

    @Override
    public String name() {
        return StringUtils.join(Collections2.transform(jsonMapperFactories, new Function<JsonMapperFactory, String>() {
            @Override
            public String apply(JsonMapperFactory input) {
                return input.name();
            }
        }), ", ");
    }
}
