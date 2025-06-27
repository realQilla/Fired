package net.qilla.fired.weapon.bullet;

import com.google.common.base.Preconditions;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Set;

public final class BulletRegistry {

    private static BulletRegistry INSTANCE;
    private final HashMap<String, Bullet> registry = new HashMap<>();

    private BulletRegistry() {
    }

    public @Nullable Bullet get(@NotNull String id) {
        return this.registry.get(id);
    }

    public boolean contains(@NotNull String id) {
        return this.registry.containsKey(id);
    }

    public @NotNull Set<String> keySet() {
        return Set.copyOf(this.registry.keySet());
    }

    public <T extends Bullet> @NotNull T register(@NotNull String id, @NotNull T bullet) {
        Preconditions.checkNotNull(id, "ID cannot be null!");
        Preconditions.checkNotNull(bullet, "Bullet cannot be null!");

        this.registry.put(id, bullet);
        return bullet;
    }

    public static BulletRegistry getInstance() {
        if(INSTANCE == null) INSTANCE = new BulletRegistry();
        return INSTANCE;
    }
}
