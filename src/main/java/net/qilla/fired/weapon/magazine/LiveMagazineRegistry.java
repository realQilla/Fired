package net.qilla.fired.weapon.magazine;

import com.google.common.base.Preconditions;
import net.qilla.fired.weapon.magazine.implementation.MagazineDynamic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public final class LiveMagazineRegistry {

    private static LiveMagazineRegistry INSTANCE;
    private final HashMap<String, MagazineDynamic> registry = new HashMap<>();

    private LiveMagazineRegistry() {
    }

    public static LiveMagazineRegistry getInstance() {
        if(INSTANCE == null) INSTANCE = new LiveMagazineRegistry();
        return INSTANCE;
    }

    public <T extends MagazineDynamic> T register(@NotNull T magazine) {
        Preconditions.checkNotNull(magazine, "Magazine cannot be null!");
        this.registry.put(magazine.getUUID(), magazine);
        return magazine;
    }

    public boolean contains(@NotNull String id) {
        return this.registry.containsKey(id);
    }

    public @Nullable MagazineDynamic getMag(@NotNull String id) {
        return registry.get(id);
    }
}
