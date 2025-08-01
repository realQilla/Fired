package net.qilla.fired.player.data;

import com.google.common.util.concurrent.AtomicDouble;
import net.qilla.fired.Fired;
import net.qilla.qlibrary.util.QParticle;
import net.qilla.qlibrary.util.tools.NumUtil;
import net.qilla.qlibrary.util.tools.PlayerUtil;
import net.qilla.qlibrary.util.tools.QRunnable;
import net.qilla.qlibrary.util.tools.QTask;
import org.bukkit.Material;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class EntityData {

    private static final Plugin PLUGIN = Fired.getInstance();
    private static final long BLEED_RATE = 1250;

    private final LivingEntity entity;
    private final AtomicDouble bleedAmount = new AtomicDouble(0.0);
    private final AtomicBoolean isBleeding = new AtomicBoolean(false);

    public EntityData(@NotNull LivingEntity entity) {
        this.entity = entity;
    }

    public void bleedEntity(float amount) {
        this.bleedAmount.addAndGet(amount);

        if(this.isBleeding.get() || this.bleedAmount.get() <= 2.0) return;
        this.isBleeding.set(true);

        new QRunnable(new QTask() {
            @Override
            public void run() {
                double finalBleed = 1 * Math.pow(EntityData.this.bleedAmount.get(), 0.45);

                if(finalBleed < 1.0 || entity.isDead()) {
                    EntityData.this.bleedAmount.set(0.0);
                    cancel();
                    return;
                }

                DamageSource damageSource = DamageSource.builder(DamageType.GENERIC).build();
                double finalDamage = Math.min(finalBleed, entity.getHealth() - 0.1);

                EntityData.this.bleedAmount.addAndGet(-finalBleed);
                entity.damage(finalDamage, damageSource);
                entity.setNoDamageTicks(0);
                PlayerUtil.particle(entity.getLocation().add(0, 1.25f, 0), QParticle.of(
                        Material.REDSTONE_BLOCK,
                        (int) NumUtil.lerp(8, 32, finalBleed, 4),
                        0.33f));
            }
        }, null, onComplete -> {
            EntityData.this.isBleeding.set(false);
        }).runSync(PLUGIN, Executors.newSingleThreadScheduledExecutor(), 1750, BLEED_RATE, TimeUnit.MILLISECONDS);

    }

    public void resetBleeding() {
        this.bleedAmount.set(0.0);
    }
}
