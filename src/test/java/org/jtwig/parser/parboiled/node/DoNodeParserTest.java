package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.SetNode;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DoNodeParserTest extends AbstractParserTest {
    private DoNodeParser underTest = context.parser(DoNodeParser.class);

    @Test
    public void set() throws Exception {
        ParsingResult<SetNode> result = parse(underTest.NodeRule(), "{% do 1 + 123 %}");

        assertThat(result.matched, is(true));
        assertThat(result.valueStack.isEmpty(), is(false));
    }

}