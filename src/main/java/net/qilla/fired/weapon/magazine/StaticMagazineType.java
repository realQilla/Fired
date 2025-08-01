package net.qilla.fired.weapon.magazine;

import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.magazine.implementation.*;
import org.jetbrains.annotations.NotNull;

public final class StaticMagazineType<T extends MagazineStatic> {

    // Shotgun
    public static final StaticMagazineType<ShellMagazine.DB12> SHOTGUN_DB12 = of(ShellMagazine.DB12::new);
    public static final StaticMagazineType<ShellMagazine.MK7P> SHOTGUN_MK7P = of(ShellMagazine.MK7P::new);
    public static final StaticMagazineType<ShellMagazine.SL1> SHOTGUN_SL1 = of(ShellMagazine.SL1::new);

    // Pistol
    public static final StaticMagazineType<PistolMagazine.HM6> PISTOL_HM6 = of(PistolMagazine.HM6::new);
    public static final StaticMagazineType<PistolMagazine.WR5> PISTOL_WR5 = of(PistolMagazine.WR5::new);

    // Heavy
    public static final StaticMagazineType<HeavyMagazine.BR7> HEAVY_BR7 = of(HeavyMagazine.BR7::new);

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
        @NotNull T create(@NotNull Gun gun);
    }
}