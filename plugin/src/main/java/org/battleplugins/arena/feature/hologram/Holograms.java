package org.battleplugins.arena.feature.hologram;

import net.kyori.adventure.text.Component;
import org.battleplugins.arena.competition.LiveCompetition;
import org.battleplugins.arena.feature.FeatureController;
import org.bukkit.Location;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * API for holograms used in BattleArena.
 */
@ApiStatus.Experimental
public class Holograms extends FeatureController<HologramFeature> {
    private static HologramFeature instance;

    /**
     * Creates a {@link Hologram} at the given location with the given lines.
     *
     * @param location the location to create the hologram at
     * @param lines the lines of the hologram
     * @return the created hologram
     */
    public static Hologram createHologram(LiveCompetition<?> competition, Location location, Component... lines) {
        HologramFeature instance = instance();
        if (instance == null) {
            return new NoopHologram(competition, location);
        }

        return instance.createHologram(competition, location, lines);
    }

    /**
     * Removes a {@link Hologram} from a competition.
     *
     * @param hologram the hologram to remove
     */
    public static void removeHologram(Hologram hologram) {
        HologramFeature instance = instance();
        if (instance != null) {
            instance.removeHologram(hologram);
        }
    }

    @Nullable
    private static HologramFeature instance() {
        if (instance == null) {
            instance = createInstance(HologramFeature.class);
        }

        return instance;
    }

    public static void register(HologramFeature feature) {
        registerFeature(HologramFeature.class, feature);
    }
}
