package net.qilla.fired.weapon.bullet;

import net.qilla.fired.Fired;
import net.qilla.fired.weapon.bullet.implementation.*;
import org.jetbrains.annotations.NotNull;

public final class BulletType {

    private static final Fired PLUGIN = Fired.getInstance();

    // Assault Bullets
    public static final AssaultBullet A_SD = register(new AssaultBullet.ASDRound("a_sd"));
    public static final AssaultBullet A_HV = register(new AssaultBullet.AHVRound("a_hv"));
    public static final AssaultBullet A_H = register(new AssaultBullet.AHRound("a_h"));
    public static final AssaultBullet A_OS = register(new AssaultBullet.AOSRound("a_os"));

    // Pistol Bullets
    public static final PistolBullet P_SD = register(new PistolBullet.PSDRound("p_sd"));

    // Heavy Bullets
    public static final HeavyBullet H_SD = register(new HeavyBullet.HSDRound("h_sd"));

    // Shotgun Bullets
    public static final ShellBullet S_SD = register(new ShellBullet.SSDRound("s_sd"));
    public static final ShellBullet S_S = register(new ShellBullet.SSRound("s_s"));

    private static <T extends Bullet> @NotNull T register(@NotNull T bullet) {
        return PLUGIN.getBulletReg().register(bullet.getID(), bullet);
    }
}
