package net.qilla.fired.player.data;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class EntityDataRegistry {

    private static EntityDataRegistry INSTANCE;
    private final ConcurrentHashMap<String, EntityData> registry = new ConcurrentHashMap<>();

    private EntityDataRegistry() {
    }

    public @NotNull EntityData getOrCreate(@NotNull LivingEntity entity) {
        String uuid = entity.getUniqueId().toString();

        if(entity instanceof Player) return this.registry.computeIfAbsent(uuid, val -> new PlayerData(entity));
        return this.registry.computeIfAbsent(uuid, val -> new EntityData(entity));
    }

    public @NotNull Optional<EntityData> get(@NotNull String uuid) {
        return Optional.ofNullable(this.registry.get(uuid));
    }

    public @NotNull Optional<EntityData> set(@NotNull String uuid, @NotNull EntityData entityData) {
        return Optional.ofNullable(this.registry.put(uuid, entityData));
    }

    public @NotNull Optional<EntityData> remove(@NotNull String uuid) {
        return Optional.ofNullable(this.registry.remove(uuid));
    }

    public @NotNull Optional<EntityData> remove(@NotNull LivingEntity entity) {
        return Optional.ofNullable(this.registry.remove(entity.getUniqueId().toString()));
    }

    public boolean contains(@NotNull String uuid) {
        return this.registry.containsKey(uuid);
    }

    public @NotNull Set<String> keySet() {
        return this.registry.keySet();
    }

    public @NotNull Collection<? extends EntityData> values() {
        return this.registry.values();
    }

    public @NotNull List<? extends EntityData> asList() {
        return new ArrayList<>(this.registry.values());
    }

    public static EntityDataRegistry getInstance() {
        if(INSTANCE == null) INSTANCE = new EntityDataRegistry();
        return INSTANCE;
    }
}
