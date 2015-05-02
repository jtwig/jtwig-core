package org.jtwig.util;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ExpressionUtilsTest {

    @Test
    public void isTrue() throws Exception {
        boolean result = ExpressionUtils.isTrue(true);
        assertThat(result, is(true));

        result = ExpressionUtils.isTrue(false);
        assertThat(result, is(false));

        result = ExpressionUtils.isTrue(null);
        assertThat(result, is(false));

        result = ExpressionUtils.isTrue(new ArrayList<>());
        assertThat(result, is(false));

        result = ExpressionUtils.isTrue(new Object[]{});
        assertThat(result, is(false));

        result = ExpressionUtils.isTrue(new Object());
        assertThat(result, is(true));

        result = ExpressionUtils.isTrue(0);
        assertThat(result, is(false));

        result = ExpressionUtils.isTrue(1);
        assertThat(result, is(true));

        result = ExpressionUtils.isTrue(0.0D);
        assertThat(result, is(false));

        result = ExpressionUtils.isTrue(1.1D);
        assertThat(result, is(true));

        result = ExpressionUtils.isTrue(0.1D);
        assertThat(result, is(false));

        result = ExpressionUtils.isTrue(0L);
        assertThat(result, is(false));

        result = ExpressionUtils.isTrue(1L);
        assertThat(result, is(true));

        result = ExpressionUtils.isTrue("");
        assertThat(result, is(false));

        result = ExpressionUtils.isTrue("a");
        assertThat(result, is(true));
    }

    @Test
    public void numberAsString() throws Exception {
        String result = ExpressionUtils.numberAsString(1).get();
        assertThat(result, is("1"));

        result = ExpressionUtils.numberAsString(1.1F).get();
        assertThat(result, is("1.1"));

        result = ExpressionUtils.numberAsString(1.1D).get();
        assertThat(result, is("1.1"));

        result = ExpressionUtils.numberAsString(0).get();
        assertThat(result, is("0"));

        result = ExpressionUtils.numberAsString("-1").get();
        assertThat(result, is("-1"));

        result = ExpressionUtils.numberAsString(true).get();
        assertThat(result, is("1"));

        result = ExpressionUtils.numberAsString(false).get();
        assertThat(result, is("0"));

        result = ExpressionUtils.numberAsString(null).get();
        assertThat(result, is("0"));
    }
}