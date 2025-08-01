package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.qlibrary.items.QStack;
import net.qilla.qlibrary.util.QParticle;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

public abstract class HeavyBullet extends BulletImpl {

    public HeavyBullet(@NotNull String id, @NotNull Factory<?> factory) {
        super(id, factory);
    }

    public static final class HSDRound extends HeavyBullet {
        public HSDRound(@NotNull String id) {
            super(id, new Factory<>()
                    .name(MiniMessage.miniMessage().deserialize("<white>H-SD Round"))
                    .itemStack(QStack.ofClean(Material.GHAST_TEAR))
                    .trailParticle(QParticle.of(Particle.HAPPY_VILLAGER))
                    .bulletClass(BulletClass.HEAVY)
                    .damage(40.0f)
                    .range(192)
                    .bulletSpread(0.25)
                    .bleed(8.0f)
                    .scale(0)
            );
        }
    }
}
