package net.qilla.fired.weapon.gun;

import com.google.common.base.Preconditions;
import net.qilla.fired.weapon.gun.implementation.Gun;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public final class LiveGunRegistry {

    private static LiveGunRegistry INSTANCE;
    private final HashMap<String, Gun> registry = new HashMap<>();

    private LiveGunRegistry() {
    }

    public static LiveGunRegistry getInstance() {
        if(INSTANCE == null) INSTANCE = new LiveGunRegistry();
        return INSTANCE;
    }

    public <T extends Gun> T register(@NotNull T gun) {
        Preconditions.checkNotNull(gun, "Gun cannot be null!");

        this.registry.put(gun.getUUID(), gun);
        return gun;
    }

    public boolean contains(@NotNull String id) {
        return this.registry.containsKey(id);
    }

    public @Nullable Gun getGun(@NotNull String id) {
        return registry.get(id);
    }
}
