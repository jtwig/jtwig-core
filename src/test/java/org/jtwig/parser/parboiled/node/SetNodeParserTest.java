package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.SetNode;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.jtwig.parser.parboiled.expression.FunctionExpressionParser;
import org.jtwig.parser.parboiled.expression.operator.BinaryOperatorParser;
import org.jtwig.parser.parboiled.expression.operator.UnaryOperatorParser;
import org.junit.Before;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.parboiled.Parboiled.createParser;

public class SetNodeParserTest extends AbstractParserTest {
    private SetNodeParser underTest = context.parser(SetNodeParser.class);

    @Test
    public void set() throws Exception {
        ParsingResult<SetNode> result = parse(underTest.NodeRule(), "{% set a = 123 %}");

        assertThat(result.matched, is(true));
        assertThat(result.valueStack.isEmpty(), is(false));
    }
}