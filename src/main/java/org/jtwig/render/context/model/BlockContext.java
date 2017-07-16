package org.jtwig.render.context.model;

import com.google.common.base.Optional;
import org.jtwig.model.tree.BlockNode;
import org.jtwig.resource.reference.ResourceReference;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BlockContext {
    private final Map<String, LinkedList<BlockDefinition>> blocks;

    public BlockContext(Map<String, LinkedList<BlockDefinition>> blocks) {
        this.blocks = blocks;
    }

    public static BlockContext newContext() {
        return new BlockContext(new HashMap<String, java.util.LinkedList<BlockDefinition>>());
    }

    public Optional<BlockDefinition> get(String identifier) {
        return get(identifier, 0);
    }

    public Optional<BlockDefinition> get(String identifier, int index) {
        LinkedList<BlockDefinition> stack = blocks.get(identifier);
        if (stack == null) {
            return Optional.absent();
        }

        try {
            return Optional.of(stack.get(index));
        } catch (IndexOutOfBoundsException e) {
            return Optional.absent();
        }
    }

    public Optional<BlockDefinition> pollFirst(String identifier) {
        LinkedList<BlockDefinition> stack = blocks.get(identifier);
        if (stack == null) {
            return Optional.absent();
        }

        return Optional.fromNullable(stack.pollFirst());
    }

    public void addLast(BlockNode node, ResourceReference source) {
        BlockDefinition blockDefinition = new BlockDefinition(node.getContent(), source);
        String identifier = node.getIdentifier();

        addLast(identifier, blockDefinition);
    }

    public void addLast(String identifier, BlockDefinition blockDefinition) {
        LinkedList<BlockDefinition> blockDefinitions = getOrAddStack(identifier);
        blockDefinitions.addLast(blockDefinition);
    }

    public void addFirst(BlockNode node, ResourceReference source) {
        BlockDefinition blockDefinition = new BlockDefinition(node.getContent(), source);
        String identifier = node.getIdentifier();

        addFirst(identifier, blockDefinition);
    }

    public void addFirst(String identifier, BlockDefinition blockDefinition) {
        LinkedList<BlockDefinition> blockDefinitions = getOrAddStack(identifier);
        blockDefinitions.addFirst(blockDefinition);
    }

    private LinkedList<BlockDefinition> getOrAddStack(String identifier) {
        if (!blocks.containsKey(identifier)) {
            LinkedList<BlockDefinition> blockDefinitions = new LinkedList<>();
            blocks.put(identifier, blockDefinitions);
            return blockDefinitions;
        } else {
            return blocks.get(identifier);
        }
    }
}
