package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.ForLoopNode;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ForLoopNodeParserTest extends AbstractParserTest {
    private ForLoopNodeParser underTest = context.parser(ForLoopNodeParser.class);

    @Test
    public void forLoopNodeMap() throws Exception {
        ParsingResult<ForLoopNode> result = parse(underTest.NodeRule(), "{% for a, b in test %}{% endfor %}");

        assertThat(result.matched, is(true));
        ForLoopNode loopNode = result.valueStack.pop();
        assertThat(loopNode.getKeyVariableExpression().get().getIdentifier(), is("a"));
        assertThat(loopNode.getVariableExpression().getIdentifier(), is("b"));
    }
    @Test
    public void forLoopNode() throws Exception {
        ParsingResult<ForLoopNode> result = parse(underTest.NodeRule(), "{% for a in test %}{% endfor %}");

        assertThat(result.matched, is(true));
        ForLoopNode loopNode = result.valueStack.pop();
        assertThat(loopNode.getVariableExpression().getIdentifier(), is("a"));
        assertThat(loopNode.getKeyVariableExpression().isPresent(), is(false));
    }
}