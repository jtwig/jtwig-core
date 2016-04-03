package org.jtwig.render.context.model;

import com.google.common.base.Optional;
import org.jtwig.model.tree.BlockNode;
import org.jtwig.model.tree.Node;

import java.util.HashMap;
import java.util.Map;

public class BlockContext {
    public static BlockContext newContext () {
        return new BlockContext(new HashMap<String, Node>());
    }

    private final Map<String, Node> blocks;

    public BlockContext(Map<String, Node> blocks) {
        this.blocks = blocks;
    }

    public Optional<Node> get(String identifier) {
        return Optional.fromNullable(blocks.get(identifier));
    }

    public void add(BlockNode node) {
        if (!blocks.containsKey(node.getIdentifier())) {
            blocks.put(node.getIdentifier(), node.getContent());
        }
    }
}
