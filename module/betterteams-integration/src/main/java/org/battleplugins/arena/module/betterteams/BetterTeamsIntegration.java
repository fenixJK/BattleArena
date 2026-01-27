package org.battleplugins.arena.module.betterteams;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.event.BattleArenaPostInitializeEvent;
import org.battleplugins.arena.event.BattleArenaReloadedEvent;
import org.battleplugins.arena.module.ArenaModule;
import org.battleplugins.arena.module.ArenaModuleInitializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;

/**
 * A module that hooks into BetterTeams to keep teammates together when joining team games.
 */
@ArenaModule(id = BetterTeamsIntegration.ID, name = "BetterTeams", description = "Adds BetterTeams integration for team selection.", authors = "BattlePlugins")
public class BetterTeamsIntegration implements ArenaModuleInitializer {
    public static final String ID = "betterteams";

    private BetterTeamsListener listener;

    @EventHandler
    public void onPostInitialize(BattleArenaPostInitializeEvent event) {
        this.updateListener(event.getBattleArena());
    }

    @EventHandler
    public void onReloaded(BattleArenaReloadedEvent event) {
        this.updateListener(event.getBattleArena());
    }

    private void updateListener(BattleArena plugin) {
        if (!Bukkit.getPluginManager().isPluginEnabled("BetterTeams")) {
            if (this.listener != null) {
                HandlerList.unregisterAll(this.listener);
                this.listener = null;
            }
            return;
        }

        if (this.listener == null) {
            this.listener = new BetterTeamsListener();
            Bukkit.getPluginManager().registerEvents(this.listener, plugin);
        }
    }
}
