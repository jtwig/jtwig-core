package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.IfNode;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IfNodeParserTest extends AbstractParserTest {
    private IfNodeParser underTest = context.parser(IfNodeParser.class);

    @Test
    public void ifNodeSimple() throws Exception {
        ParsingResult<IfNode> result = parse(underTest.NodeRule(), "{% if (true) %}{% endif %}");


        assertThat(result.matched, is(true));
        IfNode ifNode = result.valueStack.pop();
        assertThat(ifNode.getConditionNodes().size(), is(1));
    }

    @Test
    public void ifNodeWithElse() throws Exception {
        ParsingResult<IfNode> result = parse(underTest.NodeRule(), "{% if (true) %}{% else %}{% endif %}");


        assertThat(result.matched, is(true));
        IfNode ifNode = result.valueStack.pop();
        assertThat(ifNode.getConditionNodes().size(), is(2));
    }

    @Test
    public void ifNodeWithElseIf() throws Exception {
        ParsingResult<IfNode> result = parse(underTest.NodeRule(), "{% if (true) %}{% elseif (false) %}{% endif %}");


        assertThat(result.matched, is(true));
        IfNode ifNode = result.valueStack.pop();
        assertThat(ifNode.getConditionNodes().size(), is(2));
    }
    @Test
    public void ifNodeWithMultipleElseIf() throws Exception {
        ParsingResult<IfNode> result = parse(underTest.NodeRule(), "{% if (true) %}{% elseif (false) %}{% elseif (false) %}{% endif %}");


        assertThat(result.matched, is(true));
        IfNode ifNode = result.valueStack.pop();
        assertThat(ifNode.getConditionNodes().size(), is(3));
    }
}