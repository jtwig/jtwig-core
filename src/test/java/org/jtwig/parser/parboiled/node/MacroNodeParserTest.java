package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.MacroNode;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MacroNodeParserTest extends AbstractParserTest {
    private MacroNodeParser underTest = context.parser(MacroNodeParser.class);

    @Test
    public void macroNode() throws Exception {
        ParsingResult<MacroNode> result = parse(underTest.NodeRule(), "{% macro test() %}{% endmacro %}");

        assertThat(result.matched, is(true));
        MacroNode macroNode = result.valueStack.pop();
        assertThat(macroNode.getMacroName().getIdentifier(), is("test"));
        assertThat(macroNode.getMacroArgumentNames(), hasSize(0));

    }

    @Test
    public void macroNodeWithNames() throws Exception {
        ParsingResult<MacroNode> result = parse(underTest.NodeRule(), "{% macro test(jtwig) %}{% endmacro %}");

        assertThat(result.matched, is(true));
        MacroNode macroNode = result.valueStack.pop();
        assertThat(macroNode.getMacroName().getIdentifier(), is("test"));
        assertThat(macroNode.getMacroArgumentNames(), hasSize(1));
        assertThat(macroNode.getMacroArgumentNames().iterator().next(), is("jtwig"));

    }
}