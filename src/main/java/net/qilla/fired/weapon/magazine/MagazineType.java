package net.qilla.fired.weapon.magazine;

import net.qilla.fired.Fired;
import net.qilla.fired.weapon.magazine.implementation.*;
import org.jetbrains.annotations.NotNull;

public final class MagazineType<T extends Magazine> {

    private static final Fired PLUGIN = Fired.getInstance();

    public static final MagazineType<AssaultMagazine> ASSAULT_20 = register("a_20", AssaultMagazine.Magazine::new);
    public static final MagazineType<AssaultMagazine> ASSAULT_3000 = register("a_3000", AssaultMagazine.Drum::new);

    public static final MagazineType<PistolMagazine> PISTOL_10 = register("p_10", PistolMagazine.Magazine::new);
    public static final MagazineType<PistolMagazine> PISTOL_60 = register("p_60", PistolMagazine.Drum::new);

    public static final MagazineType<HeavyMagazine> HEAVY_10 = register("h_10", HeavyMagazine.Magazine::new);

    public static final MagazineType<ShellMagazine> SHELL_8 = register("s_8", ShellMagazine.Magazine::new);

    private final MagFactory<T> factory;


    private MagazineType(@NotNull MagazineType.MagFactory<T> factory) {
        this.factory = factory;
    }

    public @NotNull T createNew() {
        T gun = this.factory.create(this);
        LiveMagazineRegistry liveRegistry = PLUGIN.getLiveMagazineReg();

        return liveRegistry.register(gun);
    }

    private static <T extends Magazine> @NotNull MagazineType<T> register(String id, MagFactory<T> factory) {
        return PLUGIN.getMagazineReg().register(id, new MagazineType<>(factory));
    }

    @FunctionalInterface
    public interface MagFactory<T extends Magazine> {
        @NotNull T create(@NotNull MagazineType<?> magazineType);
    }
}