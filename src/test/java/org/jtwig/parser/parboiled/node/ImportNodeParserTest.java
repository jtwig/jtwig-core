package org.jtwig.parser.parboiled.node;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.model.tree.ImportNode;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class ImportNodeParserTest extends AbstractParserTest {
    private ImportNodeParser underTest = context.parser(ImportNodeParser.class);

    @Test
    public void importNode() throws Exception {
        ParsingResult<ImportNode> result = parse(underTest.NodeRule(), "{% import 'three' as test %}");

        assertThat(result.matched, is(true));
        ImportNode importNode = result.valueStack.pop();

        assertThat(importNode.getAliasIdentifier().getIdentifier(), is("test"));
        assertThat(importNode.getImportExpression(), instanceOf(ConstantExpression.class));
    }
}