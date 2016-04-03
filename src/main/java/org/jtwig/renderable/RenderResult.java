package org.jtwig.renderable;

public interface RenderResult {
    RenderResult append(String content);
    RenderResult flush ();
    String content ();
}
