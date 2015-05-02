package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.TextNode;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.jtwig.parser.parboiled.ParserContext;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TextNodeParserTest extends AbstractParserTest {
    private ParserContext parserContext = ParserContext.instance();
    private TextNodeParser underTest = parserContext.parser(TextNodeParser.class);

    @Test
    public void testTextNode() throws Exception {
        ParsingResult<TextNode> result = parse(underTest.NodeRule(), "test {{");

        assertThat(result.matched, is(true));
        TextNode textNode = result.valueStack.pop();
        assertThat(textNode.getText(), is("test "));
    }
}