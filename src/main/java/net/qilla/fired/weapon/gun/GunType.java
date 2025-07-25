package net.qilla.fired.weapon.gun;

import net.qilla.fired.Fired;
import net.qilla.fired.weapon.gun.implementation.*;
import net.qilla.fired.weapon.gun.complete.Assault;
import net.qilla.fired.weapon.gun.complete.Heavy;
import net.qilla.fired.weapon.gun.complete.Machine;
import net.qilla.fired.weapon.gun.complete.Pistol;
import net.qilla.fired.weapon.gun.complete.Shotgun;
import org.jetbrains.annotations.NotNull;

public final class GunType<T extends Gun> {

    private static final Fired PLUGIN = Fired.getInstance();

    // Assault Rifles
    public static final GunType<Assault.Fenrir6> ASSAULT_FENRIR6 = register("a_fenrir6", Assault.Fenrir6::new);
    public static final GunType<Assault.Lancia> ASSAULT_LANCIA = register("a_lancia", Assault.Lancia::new);

    // Machine Guns
    public static final GunType<Machine.Minigun> MACHINE_MINIGUN = register("m_minigun", Machine.Minigun::new);

    // Pistols
    public static final GunType<Pistol.PistolBrim44> PISTOL_BRIM44 = register("p_brim44", Pistol.PistolBrim44::new);
    public static final GunType<Pistol.TalonMK2> PISTOL_TALONMK2 = register("p_talonmk2", Pistol.TalonMK2::new);

    // Heavy Rifles
    public static final GunType<Heavy.Hadrian> HEAVY_HADRIAN = register("h_hadrian", Heavy.Hadrian::new);

    // Shot Guns
    public static final GunType<Shotgun.Graveshot> SHOTGUN_GRAVESHOT = register("s_graveshot", Shotgun.Graveshot::new);
    public static final GunType<Shotgun.ShotgunForge12> SHOTGUN_FORGE12 = register("s_forge12", Shotgun.ShotgunForge12::new);

    private final GunType.GunFactory<T> factory;

    private GunType(@NotNull GunType.GunFactory<T> factory) {
        this.factory = factory;
    }

    public @NotNull T createNew() {
        T gun = this.factory.create(this);
        LiveGunRegistry liveRegistry = PLUGIN.getLiveGunReg();

        return liveRegistry.register(gun);
    }

    private static <T extends Gun> @NotNull GunType<T> register(String id, GunType.GunFactory<T> factory) {
        return PLUGIN.getGunReg().register(id, new GunType<>(factory));
    }

    @FunctionalInterface
    public interface GunFactory<T extends Gun> {
        @NotNull T create(@NotNull GunType<?> gunType);
    }
}