package org.jtwig.parser.parboiled.node;

import org.jtwig.model.expression.MapExpression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.tree.IncludeNode;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class IncludeNodeParserTest extends AbstractParserTest {
    private IncludeNodeParser underTest = context.parser(IncludeNodeParser.class);

    @Test
    public void NodeRuleSimple() throws Exception {
        ParsingResult<IncludeNode> result = parse(underTest.NodeRule(), "{% include 'test' %}");

        assertThat(result.matched, is(true));
        IncludeNode node = result.valueStack.pop();
        assertThat(node.isIgnoreMissing(), is(false));
        assertThat(node.isInheritModel(), is(false));
        assertThat(node.getMapExpression(), instanceOf(MapExpression.class));
    }

    @Test
    public void NodeRuleIgnoreMissing() throws Exception {
        ParsingResult<IncludeNode> result = parse(underTest.NodeRule(), "{% include 'test' ignore missing %}");

        assertThat(result.matched, is(true));
        IncludeNode node = result.valueStack.pop();
        assertThat(node.isIgnoreMissing(), is(true));
        assertThat(node.isInheritModel(), is(false));
        assertThat(node.getMapExpression(), instanceOf(MapExpression.class));
    }

    @Test
    public void NodeRuleIgnoreMissingOnly() throws Exception {
        ParsingResult<IncludeNode> result = parse(underTest.NodeRule(), "{% include 'test' ignore missing only %}");

        assertThat(result.matched, is(true));
        IncludeNode node = result.valueStack.pop();
        assertThat(node.isIgnoreMissing(), is(true));
        assertThat(node.isInheritModel(), is(true));
        assertThat(node.getMapExpression(), instanceOf(MapExpression.class));
    }

    @Test
    public void NodeRuleIgnoreMissingWithOnly() throws Exception {
        ParsingResult<IncludeNode> result = parse(underTest.NodeRule(), "{% include 'test' ignore missing with joao only %}");

        assertThat(result.matched, is(true));
        IncludeNode node = result.valueStack.pop();
        assertThat(node.isIgnoreMissing(), is(true));
        assertThat(node.isInheritModel(), is(true));
        assertThat(node.getMapExpression(), instanceOf(VariableExpression.class));
    }
}