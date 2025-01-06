package org.battleplugins.arena.module.items;

import org.battleplugins.arena.event.BattleArenaPostInitializeEvent;
import org.battleplugins.arena.feature.items.Items;
import org.battleplugins.arena.module.ArenaModule;
import org.battleplugins.arena.module.ArenaModuleInitializer;
import org.battleplugins.arena.module.items.itemsadder.ItemsAdderFeature;
import org.battleplugins.arena.module.items.mythiccrucible.MythicCrucibleFeature;
import org.battleplugins.arena.module.items.oraxen.OraxenFeature;
import org.battleplugins.arena.module.items.qualityarmory.QualityArmoryFeature;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

/**
 * A module that allows for hooking into various item provider plugins.
 */
@ArenaModule(id = ItemsIntegration.ID, priority = 100, name = "Items", description = "Adds support for hooking into various item provider plugins.", authors = "BattlePlugins")
public class ItemsIntegration implements ArenaModuleInitializer {
    public static final String ID = "items";

    @EventHandler(priority = EventPriority.LOWEST) // Load before all other modules listening on this event
    public void onPostInitialize(BattleArenaPostInitializeEvent event) {
        if (Bukkit.getPluginManager().isPluginEnabled("QualityArmory")) {
            Items.register(new QualityArmoryFeature());

            event.getBattleArena().info("QualityArmory found. Registering item integration.");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("Oraxen")) {
            Items.register(new OraxenFeature());

            event.getBattleArena().info("Oraxen found. Registering item integration.");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("ItemsAdder")) {
            Items.register(new ItemsAdderFeature());

            event.getBattleArena().info("ItemsAdder found. Registering item integration.");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("MythicCrucible")) {
            Items.register(new MythicCrucibleFeature());

            event.getBattleArena().info("MythicCrucible found. Registering item integration.");
        }
    }
}
