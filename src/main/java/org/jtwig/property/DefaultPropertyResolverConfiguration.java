package org.jtwig.property;

import org.jtwig.property.method.CompositeMethodPropertyExtractor;
import org.jtwig.property.method.MethodNameMethodPropertyExtractor;
import org.jtwig.property.method.MethodPropertyExtractor;

import java.util.Arrays;

import static java.util.Arrays.asList;

public class DefaultPropertyResolverConfiguration extends PropertyResolverConfiguration {
    public DefaultPropertyResolverConfiguration() {
        super(asList(
                new ValueContextPropertyResolver(),
                new MacroPropertyResolver(),
                new MethodPropertyResolver(new CompositeMethodPropertyExtractor(Arrays.<MethodPropertyExtractor>asList(
                        new MethodNameMethodPropertyExtractor(MethodNameMethodPropertyExtractor.exactlyEqual()),
                        new MethodNameMethodPropertyExtractor(MethodNameMethodPropertyExtractor.prefixedEqual("get")),
                        new MethodNameMethodPropertyExtractor(MethodNameMethodPropertyExtractor.prefixedEqual("is")),
                        new MethodNameMethodPropertyExtractor(MethodNameMethodPropertyExtractor.prefixedEqual("has"))
                ))),
                new FieldPropertyResolver(true),
                new MapPropertyResolver()
        ));
    }
}
