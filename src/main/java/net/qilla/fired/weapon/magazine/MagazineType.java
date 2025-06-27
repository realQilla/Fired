package net.qilla.fired.weapon.magazine;

import net.qilla.fired.Fired;
import net.qilla.fired.weapon.magazine.implementation.*;
import org.jetbrains.annotations.NotNull;

public final class MagazineType<T extends Magazine> {

    private static final Fired PLUGIN = Fired.getInstance();

    public static final MagazineType<RifleMagazine> RIFLE_20 = register("rifle_20", RifleMagazine::new, 20);
    public static final MagazineType<RifleMagazine> RIFLE_40 = register("rifle_40", RifleMagazine::new, 40);
    public static final MagazineType<RifleDrum> RIFLE_80 = register("rifle_80", RifleDrum::new, 80);
    public static final MagazineType<RifleDrum> RIFLE_160 = register("rifle_160", RifleDrum::new, 160);

    public static final MagazineType<PistolMagazine> PISTOL_10 = register("pistol_10", PistolMagazine::new, 10);
    public static final MagazineType<PistolMagazine> PISTOL_20 = register("pistol_20", PistolMagazine::new, 20);
    public static final MagazineType<PistolDrum> PISTOL_60 = register("pistol_60", PistolDrum::new, 60);

    private final MagFactory<T> factory;
    private final int capacity;

    private MagazineType(@NotNull MagazineType.MagFactory<T> factory, int capacity) {
        this.factory = factory;
        this.capacity = capacity;
    }

    public @NotNull T createNew() {
        T gun = this.factory.create(this, capacity);
        LiveMagazineRegistry liveRegistry = PLUGIN.getLiveMagazineReg();

        return liveRegistry.register(gun);
    }

    private static <T extends Magazine> @NotNull MagazineType<T> register(String id, MagFactory<T> factory, int capacity) {
        return PLUGIN.getMagazineReg().register(id, new MagazineType<>(factory, capacity));
    }

    @FunctionalInterface
    public interface MagFactory<T extends Magazine> {
        @NotNull T create(@NotNull MagazineType<?> magazineType, int capacity);
    }
}