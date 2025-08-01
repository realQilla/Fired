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

    // Assault Rifle
    public static final GunType<Assault.CTR9> ASSAULT_CTR9 = register("a_ctr9", Assault.CTR9::new);
    public static final GunType<Assault.CR20> ASSAULT_CR20 = register("a_cr20", Assault.CR20::new);

    // Machine Gun
    public static final GunType<Machine.MGX240> MACHINE_MGX240 = register("m_mgx240", Machine.MGX240::new);

    // Pistol
    public static final GunType<Pistol.HM6> PISTOL_HM6 = register("p_hm6", Pistol.HM6::new);
    public static final GunType<Pistol.CT11> PISTOL_CT11 = register("p_ct11", Pistol.CT11::new);
    public static final GunType<Pistol.WR5> PISTOL_WR5 = register("p_wr5", Pistol.WR5::new);

    // Heavy Rifle
    public static final GunType<Heavy.MX3> HEAVY_hx3 = register("h_hx3", Heavy.MX3::new);
    public static final GunType<Heavy.BR7> HEAVY_BR7 = register("h_br7", Heavy.BR7::new);

    // Shotgun
    public static final GunType<Shotgun.MRK9> SHOTGUN_MRK9 = register("s_mrk9", Shotgun.MRK9::new);
    public static final GunType<Shotgun.BR6> SHOTGUN_BR6 = register("s_br6", Shotgun.BR6::new);
    public static final GunType<Shotgun.DB12> SHOTGUN_DB12 = register("s_db12", Shotgun.DB12::new);
    public static final GunType<Shotgun.MK7P> SHOTGUN_MK7P = register("s_mk7p", Shotgun.MK7P::new);
    public static final GunType<Shotgun.SL1> SHOTGUN_SL1 = register("s_sl1", Shotgun.SL1::new);

    private final GunType.GunFactory<T> factory;

    private GunType(@NotNull GunFactory<T> factory) {
        this.factory = factory;
    }

    public @NotNull T create() {
        T gun = this.factory.create();
        LiveGunRegistry liveRegistry = PLUGIN.getLiveGunReg();

        return liveRegistry.register(gun);
    }

    private static <T extends Gun> @NotNull GunType<T> register(String id, GunFactory<T> factory) {
        return PLUGIN.getGunReg().register(id, new GunType<>(factory));
    }

    @FunctionalInterface
    public interface GunFactory<T extends Gun> {
        @NotNull T create();
    }
}