package org.jtwig.integration.node;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import java.io.OutputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FlushNodeTest extends AbstractIntegrationTest {
    @Test
    public void simpleFlush() throws Exception {
        OutputStream outputStream = mock(OutputStream.class);

        JtwigTemplate result = JtwigTemplate.inlineTemplate("{% flush %}");
        result.render(JtwigModel.newModel(), outputStream);

        verify(outputStream).flush();
    }
}
