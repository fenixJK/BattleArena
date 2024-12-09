package org.battleplugins.arena.module.hologram.fancyholograms;

import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.libs.chatcolorhandler.ModernChatColorHandler;
import de.oliver.fancyholograms.libs.chatcolorhandler.messengers.MiniMessageMessenger;
import net.kyori.adventure.text.Component;
import org.battleplugins.arena.competition.LiveCompetition;
import org.battleplugins.arena.feature.hologram.Hologram;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.List;

public class FancyHologram implements Hologram {
    private final LiveCompetition<?> competition;
    private final de.oliver.fancyholograms.api.hologram.Hologram impl;

    public FancyHologram(LiveCompetition<?> competition, de.oliver.fancyholograms.api.hologram.Hologram impl) {
        this.competition = competition;
        this.impl = impl;
    }

    @Override
    public LiveCompetition<?> getCompetition() {
        return this.competition;
    }

    @Override
    public Location getLocation() {
        return this.impl.getData().getLocation();
    }

    @Override
    public List<Component> getLines() {
        if (this.impl.getData() instanceof TextHologramData data) {
            List<String> stringLines = data.getText();
            return ModernChatColorHandler.translate(stringLines);
        }

        return List.of();
    }

    @Override
    public void setLines(Component... lines) {
        if (this.impl.getData() instanceof TextHologramData data) {
            List<String> stringLines = Arrays.stream(lines).map(MiniMessageMessenger.MINI_MESSAGE::serialize).toList();
            data.setText(stringLines);
        }
    }

    @Override
    public void addLine(Component line) {
        if (this.impl.getData() instanceof TextHologramData data) {
            data.addLine(MiniMessageMessenger.MINI_MESSAGE.serialize(line));
        }
    }

    @Override
    public void removeLine(int index) {
        if (this.impl.getData() instanceof TextHologramData data) {
            data.removeLine(index);
        }
    }

    de.oliver.fancyholograms.api.hologram.Hologram getImpl() {
        return this.impl;
    }
}
