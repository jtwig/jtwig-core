package org.jtwig.parser.parboiled.node;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.model.tree.ExtendsNode;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class ExtendsNodeParserTest extends AbstractParserTest {
    private ExtendsNodeParser underTest = context.parser(ExtendsNodeParser.class);

    @Test
    public void extendsNode() throws Exception {
        ParsingResult<ExtendsNode> result = parse(underTest.NodeRule(), "{% extends 'hello' %}{% set ola = 1 %}{% block one %}{% endblock %}");

        assertThat(result.matched, is(true));
        ExtendsNode extendsNode = result.valueStack.pop();
        assertThat(extendsNode.getExtendsExpression(), instanceOf(ConstantExpression.class));
        assertThat(extendsNode.getNodes().size(), is(2));
    }
}