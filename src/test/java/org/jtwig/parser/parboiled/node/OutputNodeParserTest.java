package org.jtwig.parser.parboiled.node;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.tree.OutputNode;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class OutputNodeParserTest extends AbstractParserTest {
    private OutputNodeParser underTest = context.parser(OutputNodeParser.class);

    @Test
    public void output() throws Exception {
        ParsingResult<OutputNode> result = parse(underTest.NodeRule(), "{{ one }}");

        assertThat(result.matched, is(true));
        OutputNode outputNode = result.valueStack.pop();
        assertThat(outputNode.getExpression(), instanceOf(VariableExpression.class));
    }
}