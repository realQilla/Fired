package net.qilla.fired.weapon.bullet;

import net.qilla.fired.Fired;
import net.qilla.fired.weapon.bullet.implementation.*;
import org.jetbrains.annotations.NotNull;

public final class BulletType {

    private static final Fired PLUGIN = Fired.getInstance();

    public static final RifleBullet RIFLE_REGULAR = register(new RifleBullet("rifle_regular"));
    public static final RifleHVBullet RIFLE_HIGH_VELOCITY = register(new RifleHVBullet("rifle_high_velocity"));
    public static final RifleHeavyBullet RIFLE_HEAVY = register(new RifleHeavyBullet("rifle_heavy"));
    public static final RifleOversizedBullet RIFLE_FAT = register(new RifleOversizedBullet("rifle_fat"));


    public static final PistolBullet PISTOL_REGULAR = register(new PistolBullet("pistol_regular"));

    private static <T extends Bullet> @NotNull T register(@NotNull T bullet) {
        return PLUGIN.getBulletReg().register(bullet.getID(), bullet);
    }
}
