package org.jtwig.property.resolver.request;

import com.google.common.base.Optional;
import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.model.position.Position;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PropertyNameExtractorTest {
    private PropertyNameExtractor underTest = new PropertyNameExtractor();

    @Test
    public void noVariableNeitherFunction() throws Exception {
        Position position = mock(Position.class);
        ConstantExpression expression = new ConstantExpression(position, 1);

        Optional<String> result = underTest.extract(expression);

        assertThat(result.isPresent(), is(false));
    }
}