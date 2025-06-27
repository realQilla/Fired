package net.qilla.fired.weapon.magazine;

import com.google.common.base.Preconditions;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public final class LiveMagazineRegistry {

    private static LiveMagazineRegistry INSTANCE;
    private final HashMap<String, Magazine> registry = new HashMap<>();

    private LiveMagazineRegistry() {
    }

    public static LiveMagazineRegistry getInstance() {
        if(INSTANCE == null) INSTANCE = new LiveMagazineRegistry();
        return INSTANCE;
    }

    public <T extends Magazine> T register(@NotNull T mag) {
        Preconditions.checkNotNull(mag, "Magazine cannot be null!");
        this.registry.put(mag.getUUID(), mag);
        return mag;
    }

    public boolean contains(@NotNull String id) {
        return this.registry.containsKey(id);
    }

    public @Nullable Magazine getMag(@NotNull String id) {
        return registry.get(id);
    }
}
