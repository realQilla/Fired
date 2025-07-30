package net.qilla.fired.weapon.magazine;

import com.google.common.base.Preconditions;
import net.qilla.fired.weapon.magazine.implementation.MagazineDynamic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Set;

public final class MagazineRegistry {

    private static MagazineRegistry INSTANCE;
    private final HashMap<String, DynamicMagazineType<?>> registry = new HashMap<>();

    private MagazineRegistry() {
    }

    public @Nullable MagazineDynamic createNew(@NotNull String id) {
        Preconditions.checkNotNull(id, "ID cannot be null!");
        if(!this.registry.containsKey(id)) return null;

        DynamicMagazineType<?> magazineType = this.registry.get(id);

        return magazineType.createNew();
    }

    public @Nullable DynamicMagazineType<?> getType(@NotNull String id) {
        return this.registry.get(id);
    }

    public boolean contains(@NotNull String id) {
        return this.registry.containsKey(id);
    }

    public @NotNull Set<String> keySet() {
        return Set.copyOf(this.registry.keySet());
    }

    public @NotNull <T extends MagazineDynamic> DynamicMagazineType<T> register(@NotNull String id, @NotNull DynamicMagazineType<T> magazineType) {
        Preconditions.checkNotNull(id, "ID cannot be null!");
        Preconditions.checkNotNull(magazineType, "Magazine cannot be null!");

        this.registry.put(id, magazineType);
        return magazineType;
    }

    public static MagazineRegistry getInstance() {
        if(INSTANCE == null) INSTANCE = new MagazineRegistry();
        return INSTANCE;
    }
}
