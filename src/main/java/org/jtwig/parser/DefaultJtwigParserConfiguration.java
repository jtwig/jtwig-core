package org.jtwig.parser;

import org.jtwig.model.expression.operation.binary.impl.*;
import org.jtwig.model.expression.operation.unary.impl.NegativeUnaryOperator;
import org.jtwig.model.expression.operation.unary.impl.NotUnaryOperator;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.cache.NoTemplateCacheProvider;
import org.jtwig.parser.config.SyntaxConfigurationBuilder;

import java.nio.charset.Charset;
import java.util.Collections;

import static java.util.Arrays.asList;

public class DefaultJtwigParserConfiguration extends JtwigParserConfiguration {

    public DefaultJtwigParserConfiguration() {
        super(SyntaxConfigurationBuilder.syntaxConfiguration().build(),
                Collections.<AddonParserProvider>emptyList(),
                asList(
                        new NegativeUnaryOperator(),
                        new NotUnaryOperator()
                ),
                asList(
                        new SelectionOperator(),
                        new CompositionOperator(),
                        new InOperator(),
                        new ConcatOperator(),

                        new SumOperator(),
                        new SubtractOperator(),
                        new IntDivideOperator(),
                        new IntMultiplyOperator(),
                        new DivideOperator(),
                        new MultiplyOperator(),
                        new ModOperator(),

                        new LessOrEqualOperator(),
                        new GreaterOrEqualOperator(),
                        new LessOperator(),
                        new GreaterOperator(),

                        new AndOperator(),
                        new OrOperator(),
                        new EquivalentOperator(),
                        new DifferentOperator()
                ),
                Charset.defaultCharset(),
                new NoTemplateCacheProvider());
    }
}
