package org.battleplugins.arena.module.restoration;

import org.battleplugins.arena.Arena;
import org.battleplugins.arena.ArenaPlayer;
import org.battleplugins.arena.competition.Competition;
import org.battleplugins.arena.competition.LiveCompetition;
import org.battleplugins.arena.competition.map.options.Bounds;
import org.battleplugins.arena.event.action.EventAction;
import org.battleplugins.arena.resolver.Resolvable;

import java.util.Map;
import java.util.Optional;

public class RestoreArenaAction extends EventAction {

    public RestoreArenaAction(Map<String, String> params) {
        super(params);
    }

    @Override
    public void postProcess(Arena arena, Competition<?> competition, Resolvable resolvable) {
        if (!arena.isModuleEnabled(ArenaRestoration.ID)) {
            return;
        }

        if (!(competition instanceof LiveCompetition<?> liveCompetition)) {
            return; // Cannot restore a non-live competition
        }

        Optional<ArenaRestoration> moduleOpt = arena.getPlugin()
                .<ArenaRestoration>module(ArenaRestoration.ID)
                .map(module -> module.initializer(ArenaRestoration.class));

        // No restoration module (should never happen)
        if (moduleOpt.isEmpty()) {
            return;
        }

        Bounds bounds = liveCompetition.getMap().getBounds();
        if (bounds == null) {
            // No bounds
            arena.getPlugin().warn("Could not restore map {} for arena {} as the bounds are not defined!", competition.getMap().getName(), arena.getName());
            return;
        }

        ArenaRestorationUtil.restoreArena(moduleOpt.get(), arena, liveCompetition, bounds);
    }

    @Override
    public void call(ArenaPlayer arenaPlayer, Resolvable resolvable) {
        // No-op
    }
}
