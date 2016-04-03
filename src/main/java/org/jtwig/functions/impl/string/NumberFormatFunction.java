package org.jtwig.functions.impl.string;

import org.jtwig.functions.FunctionRequest;
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
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(4);

        Object number = request.get(0);

        switch (request.getNumberOfArguments()) {
            case 2:
                return numberFormat(number, getNumber(request, 1), null, null);
            case 3:
                return numberFormat(number, getNumber(request, 1), getString(request, 2), null);
            case 4:
                return numberFormat(number, getNumber(request, 1), getString(request, 2), getString(request, 3));
            default:
                return numberFormat(number, null, null, null);

        }
    }

    private String getString(FunctionRequest request, int index) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(index));
    }

    private BigDecimal getNumber(FunctionRequest request, int index) {
        return request.getEnvironment().getValueEnvironment().getNumberConverter().convert(request.get(index)).orThrow(request.getPosition(), String.format("Cannot convert argument %d of number_format to number", index + 1));
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
