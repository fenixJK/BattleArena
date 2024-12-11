package org.battleplugins.arena.module.party;

import org.battleplugins.arena.event.BattleArenaPostInitializeEvent;
import org.battleplugins.arena.feature.party.Parties;
import org.battleplugins.arena.module.ArenaModule;
import org.battleplugins.arena.module.ArenaModuleInitializer;
import org.battleplugins.arena.module.party.paf.PAFPartiesFeature;
import org.battleplugins.arena.module.party.parties.PartiesPartiesFeature;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

/**
 * A module that allows for hooking into various party plugins.
 */
@ArenaModule(id = PartyIntegration.ID, name = "Party", description = "Adds support for hooking into various Party plugins.", authors = "BattlePlugins")
public class PartyIntegration implements ArenaModuleInitializer {
    public static final String ID = "party";

    @EventHandler
    public void onPostInitialize(BattleArenaPostInitializeEvent event) {
        if (Bukkit.getPluginManager().isPluginEnabled("Spigot-Party-API-PAF")) {
            Parties.register(new PAFPartiesFeature());

            event.getBattleArena().info("Parties for Friends (API) found. Using Spigot-Party-API-PAF for party integration.");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("Parties")) {
            Parties.register(new PartiesPartiesFeature());

            event.getBattleArena().info("Parties found. Using Parties for party integration.");
        }
    }
}
