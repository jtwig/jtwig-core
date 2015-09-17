package org.jtwig.functions.impl.string;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NumberFormatFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "number_format";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(4);

        Object number = request.getArgument(0, Object.class);

        switch (request.getNumberOfArguments()) {
            case 2:
                return numberFormat(number, request.getArgument(1, BigDecimal.class), null, null);
            case 3:
                return numberFormat(number, request.getArgument(1, BigDecimal.class), request.getArgument(2, String.class), null);
            case 4:
                return numberFormat(number, request.getArgument(1, BigDecimal.class), request.getArgument(2, String.class), request.getArgument(3, String.class));
            default:
                return numberFormat(number, null, null, null);

        }
    }

    private String numberFormat (Object number, BigDecimal fractionDigits, String decimalSeparator, String groupingSeparator) {
        number = (number == null) ? 0 : number;

        DecimalFormat numberFormat = new DecimalFormat();
        DecimalFormatSymbols decimalFormatSymbols = numberFormat.getDecimalFormatSymbols();

        if (fractionDigits != null) {
            numberFormat.setMaximumFractionDigits(fractionDigits.intValue());
            numberFormat.setMinimumFractionDigits(fractionDigits.intValue());
        }

        if (decimalSeparator != null && !decimalSeparator.isEmpty())
            decimalFormatSymbols.setDecimalSeparator(decimalSeparator.charAt(0));
        else
            decimalFormatSymbols.setDecimalSeparator('.');


        if (groupingSeparator != null && !groupingSeparator.isEmpty())
            decimalFormatSymbols.setGroupingSeparator(groupingSeparator.charAt(0));
        else
            numberFormat.setGroupingUsed(false);

        numberFormat.setDecimalFormatSymbols(decimalFormatSymbols);

        return numberFormat.format(number);
    }
}
