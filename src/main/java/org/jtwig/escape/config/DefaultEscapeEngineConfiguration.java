package org.jtwig.escape.config;

import com.google.common.collect.ImmutableMap;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.escape.HtmlEscapeEngine;
import org.jtwig.escape.JavascriptEscapeEngine;
import org.jtwig.escape.NoneEscapeEngine;

public class DefaultEscapeEngineConfiguration extends EscapeEngineConfiguration {
    public DefaultEscapeEngineConfiguration() {
        super(
                "none",
                "html",
                ImmutableMap.<String, EscapeEngine>builder()
                        .put("none", NoneEscapeEngine.instance())
                        .put("html", HtmlEscapeEngine.instance())
                        .put("js", JavascriptEscapeEngine.instance())
                        .put("javascript", JavascriptEscapeEngine.instance())
                        .build());
    }
}
