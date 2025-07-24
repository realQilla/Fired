package net.qilla.fired.weapon;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public enum Rarity {
    COMMON_I(MiniMessage.miniMessage().deserialize("<!italic><dark_gray><bold>COMMON I")),
    COMMON_II(MiniMessage.miniMessage().deserialize("<!italic><gray><bold>COMMON II")),
    COMMON_III(MiniMessage.miniMessage().deserialize("<!italic><white><bold>COMMON III")),
    RARE_I(MiniMessage.miniMessage().deserialize("<!italic><dark_aqua><bold>RARE I")),
    RARE_II(MiniMessage.miniMessage().deserialize("<!italic><blue><bold>RARE II")),
    RARE_III(MiniMessage.miniMessage().deserialize("<!italic><dark_purple><bold>RARE III")),
    LEGENDARY(MiniMessage.miniMessage().deserialize("<!italic><gold><bold>LEGENDARY"));

    private final Component display;

    Rarity(@NotNull Component display) {
        this.display = display;
    }

    public @NotNull Component display() {
        return display;
    }
}
