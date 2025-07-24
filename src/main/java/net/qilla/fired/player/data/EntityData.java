package net.qilla.fired.player.data;

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
    private float bleedAmount = 0;
    private final AtomicBoolean isBleeding = new AtomicBoolean(false);

    public EntityData(@NotNull LivingEntity entity) {
        this.entity = entity;
    }

    public boolean bleedEntity(float amount) {
        this.bleedAmount += amount;

        if(this.isBleeding.get() || this.bleedAmount <= 2) return false;
        this.isBleeding.set(true);

        new QRunnable(new QTask() {
            @Override
            public void run() {
                if(EntityData.this.bleedAmount <= 1f || entity.isDead()) {
                    cancel();
                    return;
                }

                DamageSource damageSource = DamageSource.builder(DamageType.GENERIC).build();
                float curBleed = Math.min(EntityData.this.bleedAmount / 4, 4);
                EntityData.this.bleedAmount -= curBleed;

                entity.damage(Math.min(curBleed, entity.getHealth() - 0.1), damageSource);
                entity.setNoDamageTicks(0);
                PlayerUtil.particle(entity.getLocation().add(0, 1.25f, 0), QParticle.of(Material.REDSTONE_BLOCK,
                        (int) NumUtil.lerp(8, 48, EntityData.this.bleedAmount, 16),
                        0.33f));
            }
        }, null, onComplete -> {
            EntityData.this.isBleeding.set(false);
        }).runSync(PLUGIN, Executors.newSingleThreadScheduledExecutor(), 1500, BLEED_RATE, TimeUnit.MILLISECONDS);

        return true;
    }

    public void resetBleeding() {
        this.bleedAmount = 0;
    }
}
