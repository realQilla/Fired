package net.qilla.fired.weapon.bullet;

import net.qilla.fired.Fired;
import net.qilla.fired.weapon.bullet.implementation.*;
import org.jetbrains.annotations.NotNull;

public final class BulletType {

    private static final Fired PLUGIN = Fired.getInstance();

    public static final AssaultBullet A_FM = register(new AssaultBullet.AFMRound("a_fm"));
    public static final AssaultBullet A_HV = register(new AssaultBullet.AHVRound("a_hv"));
    public static final AssaultBullet A_H = register(new AssaultBullet.AHRound("a_h"));
    public static final AssaultBullet A_OS = register(new AssaultBullet.AOSRound("a_os"));

    public static final PistolBullet P_FM = register(new PistolBullet.PFMRound("p_fm"));

    public static final HeavyBullet H_FM = register(new HeavyBullet.HFMRound("h_fm"));

    public static final ShellBullet S_FM = register(new ShellBullet.SFMRound("s_fm"));
    public static final ShellBullet S_S = register(new ShellBullet.SSRound("s_s"));

    private static <T extends Bullet> @NotNull T register(@NotNull T bullet) {
        return PLUGIN.getBulletReg().register(bullet.getID(), bullet);
    }
}
