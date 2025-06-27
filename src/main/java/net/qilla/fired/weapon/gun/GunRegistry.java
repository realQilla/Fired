package net.qilla.fired.weapon.gun;

import com.google.common.base.Preconditions;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.gun.implementation.GunImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Set;

public final class GunRegistry {

    private static GunRegistry INSTANCE;
    private final HashMap<String, GunType<?>> registry = new HashMap<>();

    private GunRegistry() {
    }

    public @Nullable Gun createNew(@NotNull String id) {
        Preconditions.checkNotNull(id, "ID cannot be null!");
        if(!this.registry.containsKey(id)) return null;

        GunType<?> gunType = this.registry.get(id);

        return gunType.createNew();
    }

    public @Nullable GunType<?> getType(@NotNull String id) {
        return this.registry.get(id);
    }

    public boolean contains(@NotNull String id) {
        return this.registry.containsKey(id);
    }

    public @NotNull Set<String> keySet() {
        return Set.copyOf(this.registry.keySet());
    }

    public @NotNull <T extends Gun> GunType<T> register(@NotNull String id, @NotNull GunType<T> gunType) {
        Preconditions.checkNotNull(id, "ID cannot be null!");
        Preconditions.checkNotNull(gunType, "Gun cannot be null!");

        this.registry.put(id, gunType);
        return gunType;
    }

    public static GunRegistry getInstance() {
        if(INSTANCE == null) INSTANCE = new GunRegistry();
        return INSTANCE;
    }
}