package org.battleplugins.arena.module.hologram.decentholograms;

import eu.decentsoftware.holograms.api.holograms.HologramLine;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import eu.decentsoftware.holograms.api.holograms.enums.HologramLineType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.battleplugins.arena.competition.LiveCompetition;
import org.battleplugins.arena.feature.hologram.Hologram;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class DecentHologram implements Hologram {
    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    private final LiveCompetition<?> competition;
    private final eu.decentsoftware.holograms.api.holograms.Hologram impl;

    public DecentHologram(LiveCompetition<?> competition, eu.decentsoftware.holograms.api.holograms.Hologram impl) {
        this.competition = competition;
        this.impl = impl;
    }

    @Override
    public LiveCompetition<?> getCompetition() {
        return this.competition;
    }

    @Override
    public Location getLocation() {
        return this.impl.getLocation();
    }

    @Override
    public List<Component> getLines() {
        if (this.impl.getPages().isEmpty()) {
            return List.of();
        }

        // TODO: Support multiple pages
        HologramPage page = this.impl.getPage(0);
        List<Component> lines = new ArrayList<>();
        for (HologramLine line : page.getLines()) {
            if (line.getType() == HologramLineType.TEXT) {
                lines.add(SERIALIZER.deserialize(line.getText()));
            }
        }

        return lines;
    }

    @Override
    public void setLines(Component... lines) {
        if (this.impl.getPages().isEmpty()) {
            return;
        }

        HologramPage page = this.impl.getPage(0);
        for (int i = 0; i < page.getLines().size(); i++) {
            page.removeLine(i);
        }

        for (Component line : lines) {
            page.addLine(new HologramLine(page, this.getLocation(), SERIALIZER.serialize(line)));
        }
    }

    @Override
    public void addLine(Component line) {
        if (this.impl.getPages().isEmpty()) {
            return;
        }

        HologramPage page = this.impl.getPage(0);
        page.addLine(new HologramLine(page, this.getLocation(), SERIALIZER.serialize(line)));
    }

    @Override
    public void removeLine(int index) {
        if (this.impl.getPages().isEmpty()) {
            return;
        }

        HologramPage page = this.impl.getPage(0);
        page.removeLine(index);
    }

    public eu.decentsoftware.holograms.api.holograms.Hologram getImpl() {
        return this.impl;
    }
}
