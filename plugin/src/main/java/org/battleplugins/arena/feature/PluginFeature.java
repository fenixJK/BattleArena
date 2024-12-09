package org.battleplugins.arena.feature;

import org.bukkit.plugin.Plugin;

/**
 * Represents a {@link FeatureInstance} implemented by a plugin.
 *
 * @param <T> the feature instance
 */
public abstract class PluginFeature<T extends FeatureInstance> implements FeatureInstance {
    private final Plugin plugin;

    public PluginFeature(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isEnabled() {
        return this.plugin.isEnabled();
    }
}
