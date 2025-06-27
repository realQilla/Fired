package net.qilla.fired.misc;

import net.qilla.fired.Fired;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public final class NKey {

    private static final Plugin PLUGIN = Fired.getInstance();

    private NKey() {
    }

    public static final NamespacedKey GUN = new NamespacedKey("fired", "gun");
    public static final NamespacedKey MAGAZINE = new NamespacedKey("fired", "magazine");
    public static final NamespacedKey BULLET = new NamespacedKey("fired", "bullet");
}
