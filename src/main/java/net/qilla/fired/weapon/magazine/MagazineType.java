package net.qilla.fired.weapon.magazine;

import net.qilla.fired.Fired;
import net.qilla.fired.weapon.magazine.implementation.*;
import org.jetbrains.annotations.NotNull;

public final class MagazineType<T extends Magazine> {

    private static final Fired PLUGIN = Fired.getInstance();

    // Assault Magazines
    public static final MagazineType<AssaultMagazine> ASSAULT_10 = register("a_20", AssaultMagazine.Magazine10::new);
    public static final MagazineType<AssaultMagazine> ASSAULT_20 = register("a_20", AssaultMagazine.Magazine20::new);
    public static final MagazineType<AssaultMagazine> ASSAULT_40 = register("a_40", AssaultMagazine.Magazine40::new);
    public static final MagazineType<AssaultMagazine> ASSAULT_80 = register("a_80", AssaultMagazine.Magazine80::new);

    // Machine Magazines
    public static final MagazineType<MachineMagazine> MACHINE_250 = register("m_250", MachineMagazine.Magazine250::new);
    public static final MagazineType<MachineMagazine> MACHINE_1000 = register("m_1000", MachineMagazine.Magazine1000::new);

    // Pistol Magazines
    public static final MagazineType<PistolMagazine> PISTOL_10 = register("p_10", PistolMagazine.Magazine10::new);
    public static final MagazineType<PistolMagazine> PISTOL_20 = register("p_20", PistolMagazine.Magazine20::new);
    public static final MagazineType<PistolMagazine> PISTOL_60 = register("p_60", PistolMagazine.Magazine60::new);

    // Heavy Magazines
    public static final MagazineType<HeavyMagazine> HEAVY_10 = register("h_10", HeavyMagazine.Magazine8::new);
    public static final MagazineType<HeavyMagazine> HEAVY_24 = register("h_24", HeavyMagazine.Magazine24::new);

    // Shotgun Magazines
    public static final MagazineType<ShellMagazine> SHELL_8 = register("s_8", ShellMagazine.Magazine8::new);
    public static final MagazineType<ShellMagazine> SHELL_16 = register("s_16", ShellMagazine.Magazine16::new);
    public static final MagazineType<ShellMagazine> SHELL_24 = register("s_24", ShellMagazine.Magazine24::new);

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