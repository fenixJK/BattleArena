package org.battleplugins.arena.feature.party;

import org.battleplugins.arena.feature.FeatureController;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * API for holograms used in Parties.
 */
@ApiStatus.Experimental
public class Parties extends FeatureController<PartiesFeature> {
    private static PartiesFeature instance;

    /**
     * Gets the party of the given player UUID.
     *
     * @param uuid the uuid of the party
     * @return the party of the given player UUID
     */
    @Nullable
    public static Party getParty(UUID uuid) {
        PartiesFeature instance = instance();
        if (instance == null) {
            return null;
        }

        return instance.getParty(uuid);
    }

    public static PartiesFeature instance() {
        if (instance == null) {
            instance = createInstance(PartiesFeature.class);
        }

        return instance;
    }

    public static void register(PartiesFeature feature) {
        registerFeature(PartiesFeature.class, feature);
    }
}
