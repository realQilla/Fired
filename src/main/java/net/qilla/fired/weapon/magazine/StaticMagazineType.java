package net.qilla.fired.weapon.magazine;

import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.magazine.implementation.*;
import org.jetbrains.annotations.NotNull;

public final class StaticMagazineType<T extends MagazineStatic> {

    // Shotgun Magazines
    public static final StaticMagazineType<ShellMagazine.DoubleBarrel> DOUBLE_BARREL = of(ShellMagazine.DoubleBarrel::new);
    public static final StaticMagazineType<ShellMagazine.Pump> PUMP = of(ShellMagazine.Pump::new);

    // Heavy Magazines

    public static final StaticMagazineType<HeavyMagazine.Static8> HEAVY_8 = of(HeavyMagazine.Static8::new);

    private final MagazineFactory<T> factory;

    private StaticMagazineType(@NotNull MagazineFactory<T> factory) {
        this.factory = factory;
    }

    private static <T extends MagazineStatic> @NotNull StaticMagazineType<T> of(@NotNull MagazineFactory<T> factory) {
        return new StaticMagazineType<>(factory);
    }

    public @NotNull T createNew(@NotNull Gun gun) {
        return this.factory.create(gun);
    }

    @FunctionalInterface
    public interface MagazineFactory<T extends MagazineStatic> {
        @NotNull T create(Gun gun);
    }
}