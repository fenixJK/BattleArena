package org.battleplugins.arena.module.party.paf;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.api.party.PartyManager;
import org.battleplugins.arena.feature.PluginFeature;
import org.battleplugins.arena.feature.party.PartiesFeature;
import org.battleplugins.arena.feature.party.Party;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PAFPartiesFeature extends PluginFeature<PartiesFeature> implements PartiesFeature {

    public PAFPartiesFeature() {
        super("Spigot-Party-API-PAF");
    }

    @Override
    public @Nullable Party getParty(UUID uuid) {
        PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(uuid);
        if (player == null) {
            return null;
        }

        return new PAFParty(PartyManager.getInstance().getParty(player));
    }
}
