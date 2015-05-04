package org.jtwig.content.spaces;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HtmlSpaceRemoverTest {
    private HtmlSpaceRemover underTest = new HtmlSpaceRemover();

    @Test
    public void whiteSpaceRemover() throws Exception {
        String result = underTest.apply("<div>\n" +
                "        <strong>foo</strong>\n" +
                "    </div>");

        assertThat(result, is("<div><strong>foo</strong></div>"));
    }
}