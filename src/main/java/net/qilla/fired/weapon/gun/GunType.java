package net.qilla.fired.weapon.gun;

import net.qilla.fired.Fired;
import net.qilla.fired.weapon.gun.implementation.AssaultRifleGun;
import net.qilla.fired.weapon.gun.implementation.BurstRifleGun;
import net.qilla.fired.weapon.gun.implementation.MagnumGun;
import net.qilla.fired.weapon.gun.implementation.Gun;
import org.jetbrains.annotations.NotNull;

public final class GunType<T extends Gun> {

    private static final Fired PLUGIN = Fired.getInstance();

    public static final GunType<BurstRifleGun> rifle_burst_rifle = register("burst_rifle", BurstRifleGun::new);
    public static final GunType<AssaultRifleGun> rifle_assault_rifle = register("assault_rifle", AssaultRifleGun::new);
    public static final GunType<MagnumGun> pistol_magnum = register("magnum_pistol", MagnumGun::new);

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