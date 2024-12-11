package org.battleplugins.arena.module.hologram;

import org.battleplugins.arena.event.BattleArenaPostInitializeEvent;
import org.battleplugins.arena.feature.hologram.Holograms;
import org.battleplugins.arena.module.ArenaModule;
import org.battleplugins.arena.module.ArenaModuleInitializer;
import org.battleplugins.arena.module.hologram.decentholograms.DecentHologramsFeature;
import org.battleplugins.arena.module.hologram.fancyholograms.FancyHologramsFeature;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

/**
 * A module that allows for hooking into various hologram plugins.
 */
@ArenaModule(id = HologramIntegration.ID, name = "Hologram", description = "Adds support for hooking into various Hologram plugins.", authors = "BattlePlugins")
public class HologramIntegration implements ArenaModuleInitializer {
    public static final String ID = "hologram";

    @EventHandler
    public void onPostInitialize(BattleArenaPostInitializeEvent event) {
        if (Bukkit.getPluginManager().isPluginEnabled("FancyHolograms")) {
            Holograms.register(new FancyHologramsFeature());

            event.getBattleArena().info("FancyHolograms found. Using FancyHolograms for hologram integration.");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("DecentHolograms")) {
            Holograms.register(new DecentHologramsFeature());

            event.getBattleArena().info("DecentHolograms found. Using DecentHolograms for hologram integration.");
        }
    }
}
