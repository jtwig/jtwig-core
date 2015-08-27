package org.jtwig.context.model;

import com.google.common.base.Optional;
import org.jtwig.context.values.ValueContext;
import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;

import java.util.Map;

public class ResourceContext {
    private final Resource resource;
    private final Map<String, Macro> macros;
    private final Map<String, Renderable> blocks;
    private final ValueContext valueContext;
    private Optional<String> currentBlock = Optional.absent();
    private Optional<Resource> parent = Optional.absent();

    public ResourceContext(Resource resource, Map<String, Macro> macros, Map<String, Renderable> blocks, ValueContext valueContext) {
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

    public ResourceContext startBlock(String name) {
        this.currentBlock = Optional.of(name);
        return this;
    }

    public ResourceContext endBlock(Renderable render) {
        this.blocks.put(currentBlock.get(), render);
        this.currentBlock = Optional.absent();
        return this;
    }

    public ResourceContext merge(ResourceContext context) {
        blocks.putAll(context.blocks);
        return this;
    }

    public MacroContext macro() {
        return new MacroContext(macros);
    }

    public Optional<String> currentBlock() {
        return currentBlock;
    }

    public Optional<Renderable> block(String blockName) {
        return Optional.fromNullable(blocks.get(blockName));
    }

    public Map<String, Renderable> blocks() {
        return blocks;
    }

    public ResourceContext parent(Resource resource) {
        this.parent = Optional.fromNullable(resource);
        return this;
    }

    public Optional<Resource> parent() {
        return parent;
    }
}
