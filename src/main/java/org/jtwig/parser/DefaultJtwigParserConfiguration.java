package org.jtwig.parser;

import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.cache.NoTemplateCacheProvider;
import org.jtwig.parser.config.DefaultSyntaxConfiguration;
import org.jtwig.render.expression.calculator.operation.binary.impl.*;
import org.jtwig.render.expression.calculator.operation.unary.impl.NegativeUnaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.impl.NotUnaryOperator;

import java.util.Collections;

import static java.util.Arrays.asList;

public class DefaultJtwigParserConfiguration extends JtwigParserConfiguration {

    public DefaultJtwigParserConfiguration() {
        super(new DefaultSyntaxConfiguration(),
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
                new NoTemplateCacheProvider());
    }
}
