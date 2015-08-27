package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.BlockNode;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BlockNodeParserTest extends AbstractParserTest {
    private BlockNodeParser underTest = context.parser(BlockNodeParser.class);

    @Test
    public void block() throws Exception {
        ParsingResult<BlockNode> result = parse(underTest.NodeRule(), "{% block test %}{% endblock %}");


        assertThat(result.matched, is(true));
        BlockNode node = result.valueStack.pop();
        assertThat(node.getBlockIdentifier().getIdentifier(), is("test"));
    }


    @Test
    public void blockWithEnd() throws Exception {
        ParsingResult<BlockNode> result = parse(underTest.NodeRule(), "{% block test %}{% endblock test %}");


        assertThat(result.matched, is(true));
        BlockNode node = result.valueStack.pop();
        assertThat(node.getBlockIdentifier().getIdentifier(), is("test"));
    }


    @Test
    public void blockWithEndError() throws Exception {
        ParsingResult<BlockNode> result = parse(underTest.NodeRule(), "{% block test %}{% endblock testa %}");


        assertThat(result.matched, is(true));
        assertThat(result.hasErrors(), is(true));
    }
}