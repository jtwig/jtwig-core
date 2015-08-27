package org.jtwig.parser.parboiled.node;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.tree.EmbedNode;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class EmbedNodeParserTest extends AbstractParserTest {
    private EmbedNodeParser underTest = context.parser(EmbedNodeParser.class);

    @Test
    public void embedNode() throws Exception {
        ParsingResult<EmbedNode> result = parse(underTest.NodeRule(), "{% embed 'one' %}{% endembed %}");

        assertThat(result.matched, is(true));
        EmbedNode embedNode = result.valueStack.pop();
        assertThat(embedNode.isIgnoreMissing(), is(false));
        assertThat(embedNode.isInheritModel(), is(true));
    }

    @Test
    public void embedNodeIgnoreMissing() throws Exception {
        ParsingResult<EmbedNode> result = parse(underTest.NodeRule(), "{% embed 'one' ignore missing %}{% endembed %}");

        assertThat(result.matched, is(true));
        EmbedNode embedNode = result.valueStack.pop();
        assertThat(embedNode.isIgnoreMissing(), is(true));
        assertThat(embedNode.isInheritModel(), is(true));
    }

    @Test
    public void embedNodeIgnoreMissingDoNotExtend() throws Exception {
        ParsingResult<EmbedNode> result = parse(underTest.NodeRule(), "{% embed 'one' ignore missing only %}{% endembed %}");

        assertThat(result.matched, is(true));
        EmbedNode embedNode = result.valueStack.pop();
        assertThat(embedNode.isIgnoreMissing(), is(true));
        assertThat(embedNode.isInheritModel(), is(false));
    }

    @Test
    public void embedNodeIgnoreMissingDoNotExtendWith() throws Exception {
        ParsingResult<EmbedNode> result = parse(underTest.NodeRule(), "{% embed 'one' ignore missing with three only %}{% endembed %}");

        assertThat(result.matched, is(true));
        EmbedNode embedNode = result.valueStack.pop();
        assertThat(embedNode.isIgnoreMissing(), is(true));
        assertThat(embedNode.isInheritModel(), is(false));
        assertThat(embedNode.getMapExpression(), instanceOf(VariableExpression.class));
    }

    @Test
    public void embedNodeIgnoreMissingDoNotExtendWithWithContent() throws Exception {
        ParsingResult<EmbedNode> result = parse(underTest.NodeRule(), "{% embed 'one' ignore missing with three only %}{% block one %}{% endblock %}{% endembed %}");

        assertThat(result.matched, is(true));
        EmbedNode embedNode = result.valueStack.pop();
        assertThat(embedNode.isIgnoreMissing(), is(true));
        assertThat(embedNode.isInheritModel(), is(false));
        assertThat(embedNode.getMapExpression(), instanceOf(VariableExpression.class));
        assertThat(embedNode.getNodes().size(), is(1));
    }
}