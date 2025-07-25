package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.visualstats.BulletStat;
import net.qilla.fired.weapon.visualstats.StatHolder;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public abstract class ShellBullet extends BulletImpl {

    private final int pelletCount;

    public ShellBullet(@NotNull String id, @NotNull ShellBullet.Factory factory) {
        super(id, factory);
        this.pelletCount = factory.pelletCount;
    }

    @Override
    public void fire(@NotNull Player shooter, @NotNull Location originLoc, @NotNull Vector originDir, @NotNull Gun gun) {
        for(int i = 0; i < this.pelletCount; i++) {
            super.fire(shooter, originLoc, originDir, gun);
        }
    }

    @Override
    public @NotNull StatHolder buildStats() {
        StatHolder statHolder = super.buildStats();

        statHolder.set(BulletStat.Damage.of(super.getDamage(), this.pelletCount));

        return statHolder;
    }

    public static final class SSDRound extends ShellBullet {
        public SSDRound(@NotNull String id) {
            super(id, new ShellBullet.Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>S-SD Shell"))
                    .itemStack(QStack.ofClean(Material.ARMADILLO_SCUTE))
                    .bulletClass(BulletClass.SHELL)
                    .damage(4.5f)
                    .range(48)
                    .bulletSpread(15.0)
                    .bleed(1.25f)
                    .scale(0)
                    .pelletCount(12)
            );
        }
    }

    public static final class SSRound extends ShellBullet {
        public SSRound(@NotNull String id) {
            super(id, new ShellBullet.Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>S-S Slug"))
                    .itemStack(QStack.ofClean(Material.ARMADILLO_SCUTE))
                    .bulletClass(BulletClass.SHELL)
                    .damage(24.0f)
                    .range(96)
                    .bulletSpread(2.0)
                    .bleed(4.0f)
                    .scale(0)
                    .pelletCount(1)
            );
        }
    }

    public static final class Factory extends BulletImpl.Factory<ShellBullet.Factory> {

        private int pelletCount;

        Factory() {
            this.pelletCount = 8;
        }

        public Factory pelletCount(int pelletCount) {
            this.pelletCount = pelletCount;
            return this;
        }
    }
}
