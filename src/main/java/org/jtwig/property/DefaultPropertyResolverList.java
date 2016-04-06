package org.jtwig.property;

import org.jtwig.property.macro.MacroRender;
import org.jtwig.property.method.CompositeMethodPropertyExtractor;
import org.jtwig.property.method.MethodNameMethodPropertyExtractor;
import org.jtwig.property.method.MethodPropertyExtractor;
import org.jtwig.property.method.RetrieveArgumentsService;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.asList;

public class DefaultPropertyResolverList extends ArrayList<PropertyResolver> {
    public DefaultPropertyResolverList() {
        super(asList(
                new ValueContextPropertyResolver(),
                new MacroPropertyResolver(new MacroRender()),
                new MethodPropertyResolver(new CompositeMethodPropertyExtractor(Arrays.<MethodPropertyExtractor>asList(
                        new MethodNameMethodPropertyExtractor(RetrieveArgumentsService.instance(), MethodNameMethodPropertyExtractor.exactlyEqual()),
                        new MethodNameMethodPropertyExtractor(RetrieveArgumentsService.instance(), MethodNameMethodPropertyExtractor.prefixedEqual("get")),
                        new MethodNameMethodPropertyExtractor(RetrieveArgumentsService.instance(), MethodNameMethodPropertyExtractor.prefixedEqual("is")),
                        new MethodNameMethodPropertyExtractor(RetrieveArgumentsService.instance(), MethodNameMethodPropertyExtractor.prefixedEqual("has"))
                ))),
                new FieldPropertyResolver(true),
                new MapPropertyResolver()
        ));
    }
}
