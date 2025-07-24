package net.qilla.fired.player;

import org.jetbrains.annotations.NotNull;


public enum DisplayStat {

    HEALTH(DisplayPriority.LOW, "<red>Health: <red>%stat%</red>"),
    ARMOR(DisplayPriority.LOW, "<gray>Armor: <green>%stat%</gray>"),
    GUN_AMMO(DisplayPriority.ACTIVE, "<gray>Armor: <green>%stat%</gray>");

    private final DisplayPriority priority;
    private final String display;

    DisplayStat(@NotNull DisplayPriority priority, @NotNull String display) {
        this.priority = priority;
        this.display = display;
    }

    public @NotNull String getDisplay() {
        return display;
    }

    public @NotNull DisplayPriority getPriority() {
        return priority;
    }
}