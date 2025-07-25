package net.qilla.fired.weapon.visualstats;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;

public class StatHolder {

    private final LinkedHashMap<String, StatDisplay<?>> stats = new LinkedHashMap<>();

    public StatDisplay<?> set(@NotNull StatDisplay<?> statDisplay) {
        return stats.put(statDisplay.getID(), statDisplay);
    }

    public List<StatDisplay<?>> getStats() {
        return List.copyOf(stats.values());
    }

    public @NotNull List<Component> getLore() {
        return stats.values().stream().map(StatDisplay::getFormatted).toList();
    }
}
