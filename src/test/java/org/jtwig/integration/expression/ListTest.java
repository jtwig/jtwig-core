package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

public class ListTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void enumeratedList() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ [1, 10] }}").render(JtwigModel.newModel());

        assertThat(result, is("[1, 10]"));

    }

    @Test
    public void comprehensionListAscendingIntegers() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ [1..3] }}").render(JtwigModel.newModel());

        assertThat(result, is("[1, 2, 3]"));

    }

    @Test
    public void comprehensionListAscendingRealIntegers() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ [10..12] }}").render(JtwigModel.newModel());

        assertThat(result, is("[10, 11, 12]"));
    }

    @Test
    public void comprehensionListDescendingRealIntegers() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ [30..28] }}").render(JtwigModel.newModel());

        assertThat(result, is("[30, 29, 28]"));
    }

    @Test
    public void comprehensionListDescendingChars() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ ['c'..'a'] }}").render(JtwigModel.newModel());

        assertThat(result, is("[c, b, a]"));
    }


    @Test
    public void comprehensionListInvalid() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Unable to calculate a list from a comprehension list starting with 'a' and ending with '10'"));

        JtwigTemplate.inlineTemplate("{{ ['a'..10] }}").render(JtwigModel.newModel());
    }

    @Test
    public void comprehensionListAscendingChars() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ ['a'..'c'] }}").render(JtwigModel.newModel());

        assertThat(result, is("[a, b, c]"));
    }

    @Test
    public void invalidListMissingClosingParenthesis() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting end bracket"));

        JtwigTemplate.inlineTemplate("{{ ['a'..'c' }}").render(JtwigModel.newModel());
    }

    @Test
    public void invalidListMissingClosingParenthesisEnumeration() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting end bracket"));

        JtwigTemplate.inlineTemplate("{{ ['a', 'c' }}").render(JtwigModel.newModel());
    }
}
