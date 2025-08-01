package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.qlibrary.items.QStack;
import net.qilla.qlibrary.util.QParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class Rocket extends BulletImpl {

    private final long speed;

    public Rocket(@NotNull String id, @NotNull Rocket.Factory factory) {
        super(id, factory);
        this.speed = factory.speed;
    }

    @Override
    public void fire(@NotNull Player shooter, @NotNull Gun gun, @NotNull Location originLoc) {

    }

    public static final class SSDRound extends Rocket {
        public SSDRound(@NotNull String id) {
            super(id, new Rocket.Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>S-SD Shell"))
                    .itemStack(QStack.ofClean(Material.ARMADILLO_SCUTE))
                    .trailParticle(QParticle.of(Particle.SMOKE))
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

    public static final class Factory extends BulletImpl.Factory<Rocket.Factory> {

        private long speed;

        Factory() {
            this.speed = 8;
        }

        public Factory pelletCount(long speed) {
            this.speed = speed;
            return this;
        }
    }
}
