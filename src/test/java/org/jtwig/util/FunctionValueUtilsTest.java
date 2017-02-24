package org.jtwig.util;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

public class FunctionValueUtilsTest {
    @Test(expected = CalculationException.class)
    public void getNumber() throws Exception {
        Object value = new Object();

        FunctionRequest functionRequest = mock(FunctionRequest.class, RETURNS_DEEP_STUBS);
        Converter<BigDecimal> converter = mock(Converter.class);

        given(functionRequest.getEnvironment().getValueEnvironment().getNumberConverter()).willReturn(converter);
        given(functionRequest.get(0)).willReturn(value);
        given(converter.convert(value)).willReturn(new Converter.Result<BigDecimal>(null, false));

        FunctionValueUtils.getNumber(functionRequest, 0);
    }

}