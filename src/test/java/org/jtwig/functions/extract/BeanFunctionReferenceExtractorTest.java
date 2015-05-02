package org.jtwig.functions.extract;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.jtwig.reflection.MethodInvoker;
import org.jtwig.reflection.extractor.BeanMethodExtractor;
import org.jtwig.reflection.model.bean.BeanMethod;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.reference.FunctionReference;
import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BeanFunctionReferenceExtractorTest {
    private final BeanMethodExtractor extractor = mock(BeanMethodExtractor.class);
    private final BeanMethodFunctionExtractor methodFunctionExtractor = mock(BeanMethodFunctionExtractor.class);
    private BeanFunctionReferenceExtractor underTest = new BeanFunctionReferenceExtractor(extractor, methodFunctionExtractor);

    @Test
    public void extract() throws Exception {
        Object bean = new Object();
        BeanMethod beanMethod = mock(BeanMethod.class);
        Supplier<MethodInvoker<FunctionArgument>> methodInvoker = mock(Supplier.class);
        when(extractor.extract(eq(bean), any(Predicate.class))).thenReturn(asList(beanMethod));
        FunctionReference functionReference = mock(FunctionReference.class);
        when(methodFunctionExtractor.extract(beanMethod)).thenReturn(asList(functionReference));

        Collection<FunctionReference> result = underTest.extract(bean, methodInvoker);

        assertThat(result, hasItem(functionReference));
    }
}