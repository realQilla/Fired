package net.qilla.fired.weapon.visualstats;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;

public class StatDisplay {

    private final LinkedHashMap<String, Stat<?>> stats = new LinkedHashMap<>();

    public Stat<?> set(@NotNull Stat<?> stat) {
        return stats.put(stat.getID(), stat);
    }

    public List<Stat<?>> getStats() {
        return List.copyOf(stats.values());
    }

    public @NotNull List<Component> getLore() {
        return stats.values().stream().map(Stat::getFormatted).toList();
    }
}
