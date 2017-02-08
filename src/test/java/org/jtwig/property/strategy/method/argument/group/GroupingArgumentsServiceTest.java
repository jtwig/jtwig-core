package org.jtwig.property.strategy.method.argument.group;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.java.JavaMethod;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class GroupingArgumentsServiceTest {
    private GroupingArgumentsService underTest = new GroupingArgumentsService();

    @Test
    public void groupArgumentsNonVarArgsDifferentNumberOfArguments() throws Exception {
        JavaMethod javaMethod = mock(JavaMethod.class);
        Object[] arguments = {};

        given(javaMethod.isVarArgs()).willReturn(false);
        given(javaMethod.numberOfArguments()).willReturn(1);

        Optional<List<ArgumentGroup>> result = underTest.groupArguments(javaMethod, arguments);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void groupArgumentsVarArgsDifferentNumberOfArguments() throws Exception {
        JavaMethod javaMethod = mock(JavaMethod.class);
        Object[] arguments = {};

        given(javaMethod.isVarArgs()).willReturn(true);
        given(javaMethod.numberOfArguments()).willReturn(2);

        Optional<List<ArgumentGroup>> result = underTest.groupArguments(javaMethod, arguments);

        assertThat(result.isPresent(), is(false));
    }
}