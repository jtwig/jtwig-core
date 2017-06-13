package org.jtwig.render.listeners;

public class StagedRenderListener {
    private final RenderStage stage;
    private final RenderListener listener;

    public StagedRenderListener(RenderStage stage, RenderListener listener) {
        this.stage = stage;
        this.listener = listener;
    }

    public RenderStage getStage() {
        return stage;
    }

    public RenderListener getListener() {
        return listener;
    }
}
