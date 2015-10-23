package org.jtwig.environment;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.unary.UnaryOperator;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.cache.NoTemplateCacheProvider;
import org.junit.Test;

public class EnvironmentConfigurationBuilderTest {
    @Test
    public void parser() throws Exception {

        new EnvironmentConfigurationBuilder().parser()
                .syntax()
                    .withStartCode("{%").withEndCode("%}")
                    .withStartOutput("{{").withEndOutput("}}")
                    .withStartComment("{#").withEndComment("#}")
                .and()
                .withAddonParserProvider(customAddonParser())
                .withBinaryOperator(customBinaryOperator())
                .withUnaryOperator(customUnaryOperator())
                .withCacheProvider(new NoTemplateCacheProvider())
                .and()
                .build();

    }

    private UnaryOperator customUnaryOperator() {
        return null;
    }

    private BinaryOperator customBinaryOperator() {
        return null;
    }

    private AddonParserProvider customAddonParser() {
        return null;
    }
}