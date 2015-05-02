package org.jtwig.context.model;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.context.values.ValueContext;
import org.jtwig.render.Renderable;
import org.jtwig.render.model.OverrideRenderable;
import org.jtwig.resource.Resource;

import java.util.Map;

public class ResourceContext {
    private final Resource resource;
    private final Map<String, Macro> macros;
    private final Map<String, OverrideRenderable> blocks;
    private final ValueContext valueContext;
    private Optional<String> currentBlock = Optional.absent();

    public ResourceContext(Resource resource, Map<String, Macro> macros, Map<String, OverrideRenderable> blocks, ValueContext valueContext) {
        this.resource = resource;
        this.macros = macros;
        this.blocks = blocks;
        this.valueContext = valueContext;
    }

    public void register(String name, Macro macro) {
        macros.put(name, macro);
    }

    public Resource resource() {
        return resource;
    }

    public Renderable register(String name, Renderable renderable) {
        this.currentBlock = Optional.of(name);
        if (blocks.containsKey(name)) {
            OverrideRenderable override = new OverrideRenderable(renderable);
            blocks.get(name).overrideWith(override);
            return override;
        } else {
            OverrideRenderable overrideRenderable = new OverrideRenderable(renderable);
            blocks.put(name, overrideRenderable);
            return overrideRenderable;
        }
    }

    public ResourceContext merge(ResourceContext context) {
        blocks.putAll(context.blocks);
        return this;
    }

    public MacroContext macro() {
        return new MacroContext(macros);
    }

    public Optional<OverrideRenderable> currentBlock() {
        return currentBlock.transform(new Function<String, OverrideRenderable>() {
            @Override
            public OverrideRenderable apply(String input) {
                return blocks.get(input);
            }
        });
    }

    public Optional<Renderable> block(String blockName) {
        return Optional.<Renderable>fromNullable(blocks.get(blockName));
    }

    public void endBlock() {
        this.currentBlock = Optional.absent();
    }

    public ValueContext getValueContext() {
        return valueContext;
    }
}
