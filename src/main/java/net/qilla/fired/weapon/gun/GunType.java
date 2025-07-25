package net.qilla.fired.weapon.gun;

import net.qilla.fired.Fired;
import net.qilla.fired.weapon.gun.implementation.*;
import net.qilla.fired.weapon.gun.implementation.assault.AssaultLancia;
import net.qilla.fired.weapon.gun.implementation.assault.AssaultFenrir6;
import net.qilla.fired.weapon.gun.implementation.heavy.HeavyHadrian;
import net.qilla.fired.weapon.gun.implementation.machine.MachineMinigun;
import net.qilla.fired.weapon.gun.implementation.pistol.PistolBrim44;
import net.qilla.fired.weapon.gun.implementation.shotgun.ShotgunForge12;
import net.qilla.fired.weapon.gun.implementation.shotgun.ShotgunGraveshot;
import org.jetbrains.annotations.NotNull;

public final class GunType<T extends Gun> {

    private static final Fired PLUGIN = Fired.getInstance();

    // Assault Rifles
    public static final GunType<AssaultFenrir6> ASSAULT_FENRIR6 = register("a_fenrir6", AssaultFenrir6::new);
    public static final GunType<AssaultLancia> ASSAULT_LANCIA = register("a_lancia", AssaultLancia::new);

    // Machine Guns
    public static final GunType<MachineMinigun> MACHINE_MINIGUN = register("m_minigun", MachineMinigun::new);

    // Pistols
    public static final GunType<PistolBrim44> PISTOL_BRIM44 = register("p_brim44", PistolBrim44::new);

    // Heavy Rifles
    public static final GunType<HeavyHadrian> HEAVY_HADRIAN = register("h_hadrian", HeavyHadrian::new);

    // Shot Guns
    public static final GunType<ShotgunGraveshot> SHOTGUN_GRAVESHOT = register("s_graveshot", ShotgunGraveshot::new);
    public static final GunType<ShotgunForge12> SHOTGUN_FORGE12 = register("s_forge12", ShotgunForge12::new);

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