package org.jtwig.functions.impl;

import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NumberFunctions {
    @JtwigFunction("number_format")
    public String numberFormat (@Parameter Object number, @Parameter BigDecimal fractionDigits, @Parameter String decimalSeparator, @Parameter String groupingSeparator) {
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

    @JtwigFunction("number_format")
    public String numberFormat (@Parameter Object number, @Parameter BigDecimal fractionDigits, @Parameter String decimalSeparator) {
        return numberFormat(number, fractionDigits, decimalSeparator, null);
    }

    @JtwigFunction("number_format")
    public String numberFormat (@Parameter Object number, @Parameter BigDecimal fractionDigits) {
        return numberFormat(number, fractionDigits, null, null);
    }

    @JtwigFunction("number_format")
    public String numberFormat (@Parameter Object number) {
        return numberFormat(number, null, null, null);
    }
}
