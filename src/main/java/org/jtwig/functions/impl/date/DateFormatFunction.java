package org.jtwig.functions.impl.date;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatFunction extends SimpleJtwigFunction {
    private static NowDateSupplier dateSupplier = new NowDateSupplier() {
        @Override
        public Date now() {
            return new Date();
        }
    };

    public static void setDateSupplier (NowDateSupplier dateSupplier) {
        DateFormatFunction.dateSupplier = dateSupplier;
    }

    @Override
    public String name() {
        return "date";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(2).maximumNumberOfArguments(3);

        Date dateToFormat = null;
        Object firstArgument = request.get(0);

        if (firstArgument instanceof Date) {
            dateToFormat = (Date) firstArgument;
        } else if (firstArgument instanceof String) {
            if ("now".equals(firstArgument)) {
                dateToFormat = dateSupplier.now();
            }
        }

        if (dateToFormat == null) throw request.exception("Invalid 1st argument provided. Expected a java.util.Date or a String \"now\".");

        String format = request.getEnvironment().getValueEnvironment().getStringConverter()
                .convert(request.get(1));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        if (request.getNumberOfArguments() == 3) {
            String timeZone = request.getEnvironment().getValueEnvironment().getStringConverter()
                    .convert(request.get(2));
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        }

        return simpleDateFormat.format(dateToFormat);
    }

    public interface NowDateSupplier {
        Date now ();
    }
}
