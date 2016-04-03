package org.jtwig.util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jtwig.util.HtmlUtils.stripTags;
import static org.junit.Assert.assertThat;

public class HtmlUtilsTest {
    @Test
    public void removeCommentsTest() throws Exception {
        assertThat(stripTags("<!-- <a href='acceptance'></a>-->", ""), is(equalTo("")));
        assertThat(stripTags("<!-- -->", ""), is(equalTo("")));
        assertThat(stripTags("<!-- ", ""), is(equalTo("")));
        assertThat(stripTags("<!-- -->a", ""), is(equalTo("a")));
        assertThat(stripTags("<!-- -->a<!--asd-->", ""), is(equalTo("a")));
    }

    @Test
    public void withoutAllowedTags() throws Exception {
        assertThat(stripTags("<a>Hello</a>"), is(equalTo("Hello")));
        assertThat(stripTags("<a></a>"), is(equalTo("")));
        assertThat(stripTags("<a\nhref='asd'>Hello</a>"), is(equalTo("Hello")));
        assertThat(stripTags("<a href='asd'>Hello</a>. Joao <a>Melo</a>"), is(equalTo("Hello. Joao Melo")));
    }

    @Test
    public void withAllowedTags () {
        assertThat(stripTags("<a href='asd'>Hello</a>", "<a>"), is(equalTo("<a href='asd'>Hello</a>")));
        assertThat(stripTags("<a href='asd'\n>Hello</a>", "<a>"), is(equalTo("<a href='asd'\n>Hello</a>")));
    }
}