package org.jtwig.integration.render;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.listeners.RenderListener;
import org.jtwig.render.listeners.RenderStage;
import org.jtwig.render.listeners.StagedRenderListener;
import org.jtwig.value.context.ValueContext;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class RenderListenersTest {
    @Test
    public void listeners() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ var }}", configuration()
                .render().renderListeners()
                    .add(new StagedRenderListener(RenderStage.PRE_TEMPLATE_RENDER, new RenderListener() {
                        @Override
                        public void listen(RenderRequest request) {
                            request.getRenderContext().getCurrent(ValueContext.class)
                                    .with("var", "test");
                        }
                    }))
                .and()
                    .withStrictMode(true)
                .and()
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("test"));
    }
}
