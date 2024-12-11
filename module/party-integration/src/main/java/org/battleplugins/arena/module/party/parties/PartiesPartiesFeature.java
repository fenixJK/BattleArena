package org.battleplugins.arena.module.party.parties;

import com.alessiodp.parties.api.Parties;
import org.battleplugins.arena.feature.PluginFeature;
import org.battleplugins.arena.feature.party.PartiesFeature;
import org.battleplugins.arena.feature.party.Party;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PartiesPartiesFeature extends PluginFeature<PartiesPartiesFeature> implements PartiesFeature {

    public PartiesPartiesFeature() {
        super("Parties");
    }

    @Override
    public @Nullable Party getParty(UUID uuid) {
        com.alessiodp.parties.api.interfaces.Party party = Parties.getApi().getPartyOfPlayer(uuid);
        if (party == null) {
            return null;
        }

        return new PartiesParty(party);
    }
}
