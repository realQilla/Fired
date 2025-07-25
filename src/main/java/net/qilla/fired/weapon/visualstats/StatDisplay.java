package net.qilla.fired.weapon.visualstats;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public abstract class StatDisplay<T extends Number> {

    private final T value;
    private final String display;

    public StatDisplay(@NotNull T value, @NotNull String display) {
        this.value = value;
        this.display = display;
    }
    public abstract @NotNull String getID();

    public @NotNull T getValue() {
        return value;
    }

    public @NotNull String getDisplay() {
        return this.display;
    }

    public @NotNull Component getFormatted() {
        String str = this.display;

        str = str.replace("%value%", String.valueOf(this.value));
        return MiniMessage.miniMessage().deserialize(str);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StatDisplay<?> statDisplay) {
            return this.getID().equals(statDisplay.getID());
        } else return false;
    }
}
