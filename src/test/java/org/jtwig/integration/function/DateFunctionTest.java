package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.impl.date.DateFormatFunction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

public class DateFunctionTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void fewArguments() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage("Expected at least 2 arguments");

        JtwigTemplate.inlineTemplate("{{ date 'now' }}").render(JtwigModel.newModel());
    }

    @Test
    public void aLotOfArguments() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage("Expected at most 3 arguments");

        JtwigTemplate.inlineTemplate("{{ date('','','','') }}").render(JtwigModel.newModel());
    }

    @Test
    public void firstArgumentNotDateNeitherNow() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage("Invalid 1st argument provided. Expected a java.util.Date or a String \"now\"");

        JtwigTemplate.inlineTemplate("{{ date('','','') }}").render(JtwigModel.newModel());
    }

    @Test
    public void formatWithoutTimeZone() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 01);

        String result = JtwigTemplate.inlineTemplate("{{ date(date,'yyyy-MM-dd z') }}")
                .render(JtwigModel.newModel().with("date", calendar.getTime()));

        assertThat(result, is("2000-01-01 UTC"));
    }

    @Test
    public void formatWithTimeZone() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 01, 1, 1, 1);

        String result = JtwigTemplate.inlineTemplate("{{ date(date,'z', 'Europe/Paris') }}")
                .render(JtwigModel.newModel().with("date", calendar.getTime()));

        assertThat(result, is("CET"));
    }

    @Test
    public void nowHasDefaultImplementation() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ date('now','yyyy-MM-dd hh:mm:ss z', 'Europe/Paris') }}")
                .render(JtwigModel.newModel());

        assertThat(result, not(isEmptyString()));
    }

    @Test
    public void formatWithNow() throws Exception {
        final Date date = new Date();
        DateFormatFunction.setDateSupplier(new DateFormatFunction.NowDateSupplier() {
            @Override
            public Date now() {
                return date;
            }
        });

        String result = JtwigTemplate.inlineTemplate("{{ date('now','yyyy-MM-dd hh:mm:ss z', 'Europe/Paris') }}")
                .render(JtwigModel.newModel());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z");
        format.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        String expected = format.format(date);

        assertThat(result, is(expected));
    }
}
