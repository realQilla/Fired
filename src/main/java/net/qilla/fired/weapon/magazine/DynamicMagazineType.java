package net.qilla.fired.weapon.magazine;

import net.qilla.fired.Fired;
import net.qilla.fired.weapon.magazine.implementation.*;
import org.jetbrains.annotations.NotNull;

public final class DynamicMagazineType<T extends MagazineDynamic> {

    private static final Fired PLUGIN = Fired.getInstance();

    // Assault Magazines
    public static final DynamicMagazineType<AssaultMagazine.Magazine10> ASSAULT_10 = register("a_20", AssaultMagazine.Magazine10::new);
    public static final DynamicMagazineType<AssaultMagazine.Magazine20> ASSAULT_20 = register("a_20", AssaultMagazine.Magazine20::new);
    public static final DynamicMagazineType<AssaultMagazine.Magazine40> ASSAULT_40 = register("a_40", AssaultMagazine.Magazine40::new);
    public static final DynamicMagazineType<AssaultMagazine.Magazine80> ASSAULT_80 = register("a_80", AssaultMagazine.Magazine80::new);
    public static final DynamicMagazineType<AssaultMagazine.Dev> ASSAULT_DEV = register("a_dev", AssaultMagazine.Dev::new);

    // Machine Magazines
    public static final DynamicMagazineType<MachineMagazine.Magazine250> MACHINE_250 = register("m_250", MachineMagazine.Magazine250::new);
    public static final DynamicMagazineType<MachineMagazine.Magazine1000> MACHINE_1000 = register("m_1000", MachineMagazine.Magazine1000::new);
    public static final DynamicMagazineType<MachineMagazine.Dev> MACHINE_DEV = register("m_dev", MachineMagazine.Dev::new);

    // Pistol Magazines
    public static final DynamicMagazineType<PistolMagazine.Magazine10> PISTOL_10 = register("p_10", PistolMagazine.Magazine10::new);
    public static final DynamicMagazineType<PistolMagazine.Magazine20> PISTOL_20 = register("p_20", PistolMagazine.Magazine20::new);
    public static final DynamicMagazineType<PistolMagazine.Magazine60> PISTOL_60 = register("p_60", PistolMagazine.Magazine60::new);
    public static final DynamicMagazineType<PistolMagazine.Dev> PISTOL_DEV = register("p_dev", PistolMagazine.Dev::new);

    // Heavy Magazines
    public static final DynamicMagazineType<HeavyMagazine.Magazine8> HEAVY_10 = register("h_10", HeavyMagazine.Magazine8::new);
    public static final DynamicMagazineType<HeavyMagazine.Magazine24> HEAVY_24 = register("h_24", HeavyMagazine.Magazine24::new);
    public static final DynamicMagazineType<HeavyMagazine.Dev> HEAVY_DEV = register("h_dev", HeavyMagazine.Dev::new);

    // Shotgun Magazines
    public static final DynamicMagazineType<ShellMagazine.Dynamic8> SHELL_8 = register("s_8", ShellMagazine.Dynamic8::new);
    public static final DynamicMagazineType<ShellMagazine.Dynamic16> SHELL_16 = register("s_16", ShellMagazine.Dynamic16::new);
    public static final DynamicMagazineType<ShellMagazine.Dynamic24> SHELL_24 = register("s_24", ShellMagazine.Dynamic24::new);
    public static final DynamicMagazineType<ShellMagazine.Dev> SHELL_DEV = register("s_dev", ShellMagazine.Dev::new);

    private final MagazineFactory<T> factory;

    private DynamicMagazineType(@NotNull MagazineFactory<T> factory) {
        this.factory = factory;
    }

    public @NotNull T createNew() {
        T magazine = this.factory.create(this);
        LiveMagazineRegistry liveRegistry = PLUGIN.getLiveMagazineReg();

        return liveRegistry.register(magazine);
    }

    private static <T extends MagazineDynamic> @NotNull DynamicMagazineType<T> register(String id, MagazineFactory<T> factory) {
        return PLUGIN.getMagazineReg().register(id, new DynamicMagazineType<>(factory));
    }

    @FunctionalInterface
    public interface MagazineFactory<T extends MagazineDynamic> {
        @NotNull T create(@NotNull DynamicMagazineType<?> magazineType);
    }
}